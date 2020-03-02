### Получаю список доступных наименований пива

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
    },
    {
        "id": 3,
        "type": "светлое осветлённое",
        "inStock": true,
        "name": "Pilsner Urquell",
        "description": "непастеризованное",
        "alcohol": 4.2,
        "density": 12.0,
        "country": "Чехия",
        "price": 8.00
    }
]
```

### Получаю список доступных наименований пива отсортированных по типу темное

Request: `GET /api/beers?type=темное`

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

### Вхожу в систему как администратор

Request: `POST /api/sign-in`
    
```    
{
    "email": "alex.alexeev@gmail.com",
    "password": "password"
}
```
Response: `200 OK`
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28"
}
```

### Как администратор добавляю новое наименование пива

Request: `POST /api/beers`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`
    
```    
{
    "type": "светлое",
    "inStock": true,
    "name": "Крынiца Pilsner",
    "description": "Мягкое пиво с чистым гармоничным вкусом, искрящимся золотистым цветом и плотной белоснежной пеной. Этот сорт придется по вкусу тем, кто ищет легкое пиво, сохраняющее при этом свежий букет ароматного хмеля и деликатную горчинку во вкусе.",
    "alcohol": 4.4,
    "density": 10.5,
    "country": "Республика Беларусь",
    "price": 3.20
}
```

Response: `201 CREATED`

```
4
```

### Как администратор обновляю информацию о пиве с id = 4, меняю цену

Request: `PUT /api/beers/4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`
    
```    
{
    "type": "светлое",
    "inStock": true,
    "name": "Крынiца Pilsner",
    "description": "Мягкое пиво с чистым гармоничным вкусом, искрящимся золотистым цветом и плотной белоснежной пеной. Этот сорт придется по вкусу тем, кто ищет легкое пиво, сохраняющее при этом свежий букет ароматного хмеля и деликатную горчинку во вкусе.",
    "alcohol": 4.4,
    "density": 10.5,
    "country": "Республика Беларусь",
    "price": 5.20
}
```

Response: `201 CREATED`

```
{
    "id": 4,
    "type": "светлое",
    "inStock": true,
    "name": "Крынiца Pilsner",
    "description": "Мягкое пиво с чистым гармоничным вкусом, искрящимся золотистым цветом и плотной белоснежной пеной. Этот сорт придется по вкусу тем, кто ищет легкое пиво, сохраняющее при этом свежий букет ароматного хмеля и деликатную горчинку во вкусе.",
    "alcohol": 4.4,
    "density": 10.5,
    "country": "Республика Беларусь",
    "price": 5.20
}
```

### Как администратор удаляю пиво с id = 4

Request: `DELETE /api/beers/4`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`

### Регистрируюсь, как покупатель

Request: `POST /api/users/sign-up`
    
```    
{
    "firstName": "Антон",
    "secondName": "Антонов",
    "email": "anton.antonov@mail.ru",
    "password": "anton",
    "phone": "+375331234567"
}
```
Response: `201 CREATED`

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28"
}
```

### Оформляю заказ как аутентифицированный пользователь 

Request: `POST /api/orders`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

```
{
    "customerId": 4,
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
    "id": 3,
    "userDTO": {
        "id": 4,
        "firstName": "Антон",
        "secondName": "Антонов",
        "password": null,
        "email": "anton.antonov@mail.ru",
        "phone": "+375331234567"
    },
    "processed": false,
    "canceled": false,
    "total": 27.00,
    "customerOrders": [
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
        },
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
        }
    ]
}
```

### Отменяю заказ как авторизованный пользователь

Request: `PATCH /api/orders/2?canceled=true`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`

```
2
```

### Вхожу в систему как зарегистрированный пользователь

Request: `POST /api/sign-in`
    
```    
{
    "email": "petr.petrov@yandex.ru",
    "password": "654321"
}
```

Response: `200 OK`

```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyLnBldHJvdkB5YW5kZXgucnUiLCJleHAiOjE1ODMxMTUzNjksImlhdCI6MTU4MzA3OTM2OX0.1g_o0h5fvEobgZ5JGABbqrxA9X6Sds4bRa7CaAnOfJE"
}
```

### Оформляю заказ как аутентифицированный пользователь 

Request: `POST /api/orders`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZXRyLnBldHJvdkB5YW5kZXgucnUiLCJleHAiOjE1ODMxMTUzNjksImlhdCI6MTU4MzA3OTM2OX0.1g_o0h5fvEobgZ5JGABbqrxA9X6Sds4bRa7CaAnOfJE`

```
{
    "customerId": 2,
    "goods": [
        {
            "id": 1,
            "amount": 5
        },
        {
            "id": 3,
            "amount": 4
        }
    ]
}
```

Response: `201 CREATED`

```
{
    "id": 3,
    "userDTO": {
        "id": 2,
        "firstName": "Петр",
        "secondName": "Петров",
        "password": null,
        "email": "petr.petrov@yandex.ru",
        "phone": "+375337654321"
    },
    "processed": false,
    "canceled": false,
    "total": 57.00,
    "customerOrders": [
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
        "amount": 4
    },
    {
        "beer": {
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
        "amount": 5
        }
    ]
}
```

### Как администратор просматриваю список заказов

Response: `GET /api/orders`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Request: `200 OK`

```
[
    {
        "id": 1,
        "userDTO": {
            "id": 1,
            "firstName": "Иван",
            "secondName": "Иванов",
            "password": null,
            "email": "ivan.ivanov@mail.ru",
            "phone": "+375331234567"
        },
        "processed": true,
        "canceled": false,
        "total": 25.00,
        "customerOrders": [
            {
            "beer": {
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
            "amount": 2
            },
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
            }
        ]
    },
    {
        "id": 2,
        "userDTO": {
            "id": 4,
            "firstName": "Антон",
            "secondName": "Антонов",
            "password": null,
            "email": "anton.antonov@mail.ru",
            "phone": "+375331234567"
        },
        "processed": false,
        "canceled": true,
        "total": 27.00,
        "customerOrders": [
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
    },
    {
        "id": 3,
        "userDTO": {
            "id": 2,
            "firstName": "Петр",
            "secondName": "Петров",
            "password": null,
            "email": "petr.petrov@yandex.ru",
            "phone": "+375337654321"
        },
        "processed": false,
        "canceled": false,
        "total": 57.00,
        "customerOrders": [
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
            "amount": 4
        },
        {
            "beer": {
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
            "amount": 5
            }
        ]
    }
]
```

### Как администратор меняю статус у заказа с id = 3

Request: `PATCH /api/orders/3?status=true`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`

```
2
```

### Как администратор удаляю заказ если он обработан

Request: `DELETE /api/orders/2`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`

### Как администратор удаляю пользователя если у него нет открытых заказов

Request: `DELETE /api/users/2`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`

### Как пользователь аннулирую свой заказ

Request: `PATCH /api/orders/2?canceled=true`

Headers: `Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbi5hbnRvbm92QG1haWwucnUiLCJleHAiOjE1ODI4MjU2ODgsImlhdCI6MTU4Mjc4OTY4OH0.oz7rblKyHA0jCHaA0BjjguMldkK8z0nfHBg8cWB2K28`

Response: `200 OK`