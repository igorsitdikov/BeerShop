package com.gp.beershop.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrdersControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testAddOrder() throws Exception {
        mockMvc.perform(post("/api/admin/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"customerId\": 2,\n" +
                        "    \"goods\": [\n" +
                        "        {\n" +
                        "            \"id\": 2,\n" +
                        "            \"value\": 1\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"id\": 3,\n" +
                        "            \"value\": 3\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "    \"id\": 2,\n" +
                        "    \"customer\": {\n" +
                        "        \"id\": 2,\n" +
                        "        \"name\": \"Петр Петров\",\n" +
                        "        \"email\": \"petr.petrov@yandex.ru\",\n" +
                        "        \"phone\": \"+375337654321\"\n" +
                        "    },\n" +
                        "    \"processed\": false,\n" +
                        "    \"total\": 27.0,\n" +
                        "    \"order\": [\n" +
                        "        {\n" +
                        "            \"beer\": {\n" +
                        "                \"id\": 2,\n" +
                        "                \"type\": \"темное\",\n" +
                        "                \"in_stock\": true,\n" +
                        "                \"name\": \"Аливария\",\n" +
                        "                \"description\": \"Пиво номер 1 в Беларуси\",\n" +
                        "                \"alcohol\": 4.6,\n" +
                        "                \"density\": 10.2,\n" +
                        "                \"country\": \"Республика Беларусь\",\n" +
                        "                \"price\": 3.0\n" +
                        "            },\n" +
                        "            \"volume\": 1\n" +
                        "        },\n" +
                        "        {\n" +
                        "            \"beer\": {\n" +
                        "                \"id\": 3,\n" +
                        "                \"type\": \"светлое осветлённое\",\n" +
                        "                \"in_stock\": true,\n" +
                        "                \"name\": \"Pilsner Urquell\",\n" +
                        "                \"description\": \"непастеризованное\",\n" +
                        "                \"alcohol\": 4.2,\n" +
                        "                \"density\": 12.0,\n" +
                        "                \"country\": \"Чехия\",\n" +
                        "                \"price\": 8.0\n" +
                        "            },\n" +
                        "            \"volume\": 3\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"));
    }

    @Test
    public void testUpdateOrderById() throws Exception {
        mockMvc.perform(patch("/api/admin/orders/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"processed\": true\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "    \"id\": 2\n" +
                        "}"));
    }

    @Test
    public void testShowAllOrders() throws Exception {
        mockMvc.perform(get("/api/admin/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    {\n" +
                        "        \"id\": 1,\n" +
                        "        \"customer\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"name\": \"Иван Иванов\",\n" +
                        "            \"email\": \"ivan.ivanov@mail.ru\",\n" +
                        "            \"phone\": \"+375331234567\"\n" +
                        "        },\n" +
                        "        \"processed\": true,\n" +
                        "        \"total\": 31.0,\n" +
                        "        \"order\": [\n" +
                        "            {\n" +
                        "                \"beer\": {\n" +
                        "                    \"id\": 2,\n" +
                        "                    \"type\": \"темное\",\n" +
                        "                    \"in_stock\": true,\n" +
                        "                    \"name\": \"Аливария\",\n" +
                        "                    \"description\": \"Пиво номер 1 в Беларуси\",\n" +
                        "                    \"alcohol\": 4.6,\n" +
                        "                    \"density\": 10.2,\n" +
                        "                    \"country\": \"Республика Беларусь\",\n" +
                        "                    \"price\": 3.0\n" +
                        "                },\n" +
                        "                \"volume\": 5\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"beer\": {\n" +
                        "                    \"id\": 3,\n" +
                        "                    \"type\": \"светлое осветлённое\",\n" +
                        "                    \"in_stock\": true,\n" +
                        "                    \"name\": \"Pilsner Urquell\",\n" +
                        "                    \"description\": \"непастеризованное\",\n" +
                        "                    \"alcohol\": 4.2,\n" +
                        "                    \"density\": 12.0,\n" +
                        "                    \"country\": \"Чехия\",\n" +
                        "                    \"price\": 8.0\n" +
                        "                },\n" +
                        "                \"volume\": 2\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"id\": 2,\n" +
                        "        \"customer\": {\n" +
                        "            \"id\": 2,\n" +
                        "            \"name\": \"Петр Петров\",\n" +
                        "            \"email\": \"petr.petrov@yandex.ru\",\n" +
                        "            \"phone\": \"+375337654321\"\n" +
                        "        },\n" +
                        "        \"processed\": false,\n" +
                        "        \"total\": 27.0,\n" +
                        "        \"order\": [\n" +
                        "            {\n" +
                        "                \"beer\": {\n" +
                        "                    \"id\": 2,\n" +
                        "                    \"type\": \"темное\",\n" +
                        "                    \"in_stock\": true,\n" +
                        "                    \"name\": \"Аливария\",\n" +
                        "                    \"description\": \"Пиво номер 1 в Беларуси\",\n" +
                        "                    \"alcohol\": 4.6,\n" +
                        "                    \"density\": 10.2,\n" +
                        "                    \"country\": \"Республика Беларусь\",\n" +
                        "                    \"price\": 3.0\n" +
                        "                },\n" +
                        "                \"volume\": 1\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"beer\": {\n" +
                        "                    \"id\": 3,\n" +
                        "                    \"type\": \"светлое осветлённое\",\n" +
                        "                    \"in_stock\": true,\n" +
                        "                    \"name\": \"Pilsner Urquell\",\n" +
                        "                    \"description\": \"непастеризованное\",\n" +
                        "                    \"alcohol\": 4.2,\n" +
                        "                    \"density\": 12.0,\n" +
                        "                    \"country\": \"Чехия\",\n" +
                        "                    \"price\": 8.0\n" +
                        "                },\n" +
                        "                \"volume\": 3\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "] "));
    }
}
