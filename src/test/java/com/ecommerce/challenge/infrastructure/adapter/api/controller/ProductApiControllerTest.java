package com.ecommerce.challenge.infrastructure.adapter.api.controller;

import com.ecommerce.challenge.domain.port.service.ProductManagementService;
import com.ecommerce.challenge.infrastructure.adapter.api.domain.ProductApiModel;
import com.ecommerce.challenge.infrastructure.adapter.api.mapper.ProductApiMapper;
import com.ecommerce.challenge.infrastructure.adapter.db.DbProductManagementRepository;
import com.ecommerce.challenge.infrastructure.adapter.db.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.Assert;
import org.springframework.web.context.request.NativeWebRequest;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private NativeWebRequest nativeWebRequest;

    @Autowired
    private ProductManagementService priceService;

    @Autowired
    private ProductRepository priceRepository;

    @Autowired
    private ProductController priceApiController;

    @Autowired
    private DbProductManagementRepository dbPriceRepository;

    private final ProductApiMapper mapper = Mappers.getMapper(ProductApiMapper.class);

    private static final String  PRODUCTS_URL = "/product";
    private static final String  PRODUCT_BY_ID_URL = "/product/{pid}";
    private static final String  PRODUCT_ID = "1";

    private static final double  TEST_CASE_1_EXPECTED_PRICE = 19.99;


    @BeforeEach
    void setMockOutput() throws Exception {
        ReflectionTestUtils.setField(priceApiController, "service", priceService);
    }
    @Test
    @Order(1)

    void getAllProductsTest() throws Exception{
        Double price = executeGet(get(PRODUCTS_URL),"$[0].price");
        Assert.isTrue( price.equals(TEST_CASE_1_EXPECTED_PRICE),"Price is not equal to: " + TEST_CASE_1_EXPECTED_PRICE);
    }

    @Test
    @Order(2)
    void getProductByIdTest() throws Exception{
        Double price = executeGet(get(PRODUCT_BY_ID_URL, PRODUCT_ID),"$.price");
        Assert.isTrue( price.equals(TEST_CASE_1_EXPECTED_PRICE),"Price is not equal to: " + TEST_CASE_1_EXPECTED_PRICE);
    }
    @Test
    @Order(3)
    void updateProductTest() throws Exception{
        ProductApiModel product = new ProductApiModel();
        product.setDescription("updated description");
        product.setCurrency("EUR");
        product.setPrice(BigDecimal.valueOf(33.33));
        product.setBrandName("updated brand");

        int status = executeCreateUpdate(put(PRODUCT_BY_ID_URL, PRODUCT_ID),product);
        Assert.isTrue( status == HttpStatus.OK.value(),"Status is not 200");
    }

    @Test
    @Order(4)
    void deleteProductTest() throws Exception{
        int status = executeDelete(delete(PRODUCT_BY_ID_URL, PRODUCT_ID));
        Assert.isTrue( status == HttpStatus.NO_CONTENT.value(),"Status is not 204");
    }

    @Test
    @Order(4)
    void createProductTest() throws Exception{
        ProductApiModel product = new ProductApiModel();
        product.setDescription("Test description");
        product.setCurrency("EUR");
        product.setPrice(BigDecimal.valueOf(20.03));
        product.setBrandName("Test brand");
        int status = executeCreateUpdate(post(PRODUCTS_URL), product);
        Assert.isTrue( status == HttpStatus.CREATED.value(),"Status is not 201");
    }



    private Double executeGet(MockHttpServletRequestBuilder url, String jsonPath) throws Exception {
        MvcResult result = this.mockMvc.perform(url
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        result.getResponse().getStatus();
        return JsonPath.read(result.getResponse().getContentAsString(), jsonPath);
    }

    private int executeDelete(MockHttpServletRequestBuilder url) throws Exception {
        MvcResult result = this.mockMvc.perform(url
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return result.getResponse().getStatus();
    }

    private int executeCreateUpdate(MockHttpServletRequestBuilder url, ProductApiModel product) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        String jsonContent = objectMapper.writeValueAsString(product);
        MvcResult result = this.mockMvc.perform(url
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonContent))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        return result.getResponse().getStatus();
    }
}