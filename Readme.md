## Beer shop "АлкашНя"

## Overview

Приложение эмулирующее работу магазина по продаже пива

## Сущности

Ниже перечислены сущности в предметной области проекта их поля и связи.

### Пиво (Beer):

Поля:   
- Тип
- Наличие
- Название
- Описание
- Крепость
- Плотность
- Страна производитель
- Цена
    
### Заказ (Order)

Поля:
- Покупатель
- Статус
- Количество
- Общая стоимость
- Аннулирован
- Список заказа

Связи:
- список наименований пива, появляется при оформлении заказа ("Beer" to "Order")
- покупатель, появляется при оформлении заказа ("Customer" to "Order")
 
### Покупатель (Customer)

Поля:
- Имя
- Фамилия
- Email
- Номер телефона
     
### Администратор (Administrator)
Пользователь, который занимается оформлением заказов и добавлением, изменением и удалением наименований пива

Поля:
- Имя
- Фамилия
- Email
- Номер телефона
        
## User Stories

### BS-1 Как "Покупатель", я хочу получить список всех товаров, имеющихся в магазине с кратким описанием, и в результате получаю его

Request: `GET /api/beers`

Response: `200 OK`

```
[
    {
        "id": 1,
        "type": "светлое",
        "inStock": true,
        "name": "Лидское",
        "description": "Лучшее пиво по бабушкиным рецептам",
        "alcohol": 5.0,
        "density": 11.5,
        "country": "Республика Беларусь",
        "price": 5.00
    },
    {
        "id": 2,
        "type": "темное",
        "inStock": true,
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": 4.6,
        "density": 10.2,
        "country": "Республика Беларусь",
        "price": 3.00
    }
]
```

### BS-2 Как "Покупатель", я хочу получить список товаров, отфильтрованных по критерию "Тип" (темное) , и в результате получаю список наименований темного пива

Request: `GET /api/beers?type=${beerType}`

`Где: beerType="темное"`

Response: `200 OK`

``` 
[
    {
        "id": 2,
        "type": "темное",
        "inStock": true,
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": 4.6,
        "density": 10.2,
        "country": "Республика Беларусь",
        "price": 3.00
    }
]
```

### BS-3 Как "Покупатель", хочу зарегистрироваться, и если пользователя с таким E-mail не найдено, регистрируюсь

Request: `POST /api/users/sign-up`
    
```    
{
    "firstName": "Иван",
    "secondName": "Иванов",
    "email": "ivan.ivanov@mail.ru",
    "password": "123456",
    "phone": "+375331234567"
}
```
Response: `201 CREATED`

```
{
   "id" : 1
}
```

### BS-4 Как "Покупатель", будучи зарегистрированным пользователем, я хочу войти в систему, и, если такой пользователь существует и пароль совпадает, войти в систему

Request: `POST /api/sign-in`
    
```    
{
    "email": "ivan.ivanov@mail.ru",
    "password": "123456"
}
```
Response: `200 OK`

```
{
    token: "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0"
}
```

### BS-5 Как "Покупатель", я хочу выбрать, интересующие меня наименования пива с указанием объема, и оформить заказ, и если я авторизован, оформляю заказ

Request: 

`POST /api/orders`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0
```

```
{
    "customerId": 2,
    "goods": [
        {
            "id": 2,
            "amount": 1
        },
        {
            "id": 3,
            "amount": 3
        }
    ]
}
```

Response: `201 CREATED`

```
{
    "id": 2,
    "customer": {
        "id": 2,
        "firstName": "Петр",
        "secondName": "Петров",
        "email": "petr.petrov@yandex.ru",
        "phone": "+375337654321"
    },
    "processed": false,
    "total": 27.0,
    "canceled": false,
    "customerOrder": [
        {
            "beer": {
                "id": 2,
                "type": "темное",
                "inStock": true,
                "name": "Аливария",
                "description": "Пиво номер 1 в Беларуси",
                "alcohol": 4.6,
                "density": 10.2,
                "country": "Республика Беларусь",
                "price": 3.00
            },
            "amount": 1
        },
        {
            "beer": {
                "id": 3,
                "type": "светлое осветлённое",
                "inStock": true,
                "name": "Pilsner Urquell",
                "description": "непастеризованное",
                "alcohol": 4.2,
                "density": 12.0,
                "country": "Чехия",
                "price": 8.00
            },
            "amount": 3
        }
    ]
}
```

### BS-6 Как "Администратор", я хочу добавить новое наименование пива, и если такого наименования нет, добавляю его

Request: `POST /api/beers`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```
    
```    
{
    "type": "светлое осветлённое",
    "inStock": true,
    "name": "Pilsner Urquell",
    "description": "непастеризованное",
    "alcohol": 4.2,
    "density": 12.0,
    "country": "Чехия",
    "price": 8.00
}
```

Response: `201 CREATED`

```
{
   "id" : 3
}
```

### BS-7 Как "Администратор", я хочу изменить цену пива, и если такое наименование есть, изменяю ему цену

Request: `PUT /api/beers/${beerId}`

`Где: beerId=3`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```
    
```    
{
    "id": 3,
    "type": "светлое осветлённое",
    "inStock": true,
    "name": "Pilsner Urquell Extra",
    "description": "непастеризованное",
    "alcohol": 4.5,
    "density": 12.2,
    "country": "Чехия",
    "price": 8.30
}
```

Response: `200 OK`

```
{
    "id": 3,
    "type": "светлое осветлённое",
    "inStock": false,
    "name": "Pilsner Urquell Extra",
    "description": "непастеризованное",
    "alcohol": 4.5,
    "density": 12.2,
    "country": "Чехия",
    "price": 8.30
}
```

### BS-8 Как "Администратор", я хочу удалить наименование пива, и если такое наименование есть, удаляю его

Request: `DELETE /api/beers/${beerId}`

`Где: beerId=3`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```

Response: `200 OK`


### BS-9 Как "Администратор", хочу получить список заказов, и получаю список с информацией по каждому заказу

Request: `GET /api/orders`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```

Response: `200 OK`

```   
[
    {
        "id": 1,
        "customer": {
            "id": 1,
            "firstName": "Иван",
            "secondName": "Иванов",
            "email": "ivan.ivanov@mail.ru",
            "phone": "+375331234567"
        },
        "processed": true,
        "total": 31.0,
        "canceled": false,
        "customerOrder": [
            {
                "beer": {
                    "id": 2,
                    "type": "темное",
                    "inStock": true,
                    "name": "Аливария",
                    "description": "Пиво номер 1 в Беларуси",
                    "alcohol": 4.6,
                    "density": 10.2,
                    "country": "Республика Беларусь",
                    "price": 3.00
                },
                "amount": 5
            },
            {
                "beer": {
                    "id": 3,
                    "type": "светлое осветлённое",
                    "inStock": true,
                    "name": "Pilsner Urquell",
                    "description": "непастеризованное",
                    "alcohol": 4.2,
                    "density": 12.0,
                    "country": "Чехия",
                    "price": 8.00
                },
                "amount": 2
            }
        ]
    },
    {
        "id": 2,
        "customer": {
            "id": 2,
            "firstName": "Петр",
            "secondName": "Петров",
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321"
        },
        "processed": false,
        "total": 27.0,
        "canceled": false,
        "customerOrder": [
            {
                "beer": {
                    "id": 2,
                    "type": "темное",
                    "inStock": true,
                    "name": "Аливария",
                    "description": "Пиво номер 1 в Беларуси",
                    "alcohol": 4.6,
                    "density": 10.2,
                    "country": "Республика Беларусь",
                    "price": 3.00
                },
                "amount": 1
            },
            {
                "beer": {
                    "id": 3,
                    "type": "светлое осветлённое",
                    "inStock": true,
                    "name": "Pilsner Urquell",
                    "description": "непастеризованное",
                    "alcohol": 4.2,
                    "density": 12.0,
                    "country": "Чехия",
                    "price": 8.00
                },
                "amount": 3
            }
        ]
    }
] 
```

### BS-10 Как "Администратор", я хочу изменить статус заказа на "Обработано", меняю его

Request: `PATCH /api/orders/${orderId}?status=${status}`

`Где: orderId=2, status=true`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```
    
Response: `200 OK`

```
{
    "id": 2
}
```
#### Сергей
### BS-11 Как "Администратор", я хочу удалить заказ, и если заказ обработан удаляю его

Request: `DELETE /api/orders/${orderId}`

`Где: orderId=2`
 
```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```
    
Response: `200 OK`

#### Владислав
### BS-12 Как "Администратор", я хочу удалить "Пользователя", и если у пользователя нет открытых заказов, удаляю

Request: `DELETE /api/users/${userId}`

`Где: userId=2`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI3NDEyMTAsImlhdCI6MTU4MjcwNTIxMH0.yfCxFB_f7U7-YTF6npRWAgZK5O_M1alWbq63gq2diuk
```
    
Response: `200 OK`

#### Олег
### BS-13 Как "Покупатель", я хочу аннулировать заказ, и если у заказа статус не обработан, удаляю его

Request: `PATCH /api/orders/${orderId}`

`Где: orderId=2`

```
Headers: 

Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGV4LmFsZXhlZXZvdkB5YW5kZXgucnUiLCJleHAiOjE1ODI2ODA3MjgsImlhdCI6MTU4MjY0NDcyOH0.oxNyf3jOPRoTuywoe2-oibyVxcisvOaPTWCaX56v9-0
```
    
Response: `200 OK`