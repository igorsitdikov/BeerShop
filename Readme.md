## Beer shop "АлкашНя"

## Сущности

### Пиво (Beer):

Поля:   
- Тип
- Наличие
- Название
- Описание
- Крепость
- Плотность
- Количество
- Страна производитель
- Цена
    
### Покупатель (Customer)

Поля:
- ФИО
- Email
- Номер телефона

Связи:
- появляются при оформлении заказа ("Beer" to "Customer")
     
### Администратор (Administrator)
Пользователь, который занимается оформлением заказов и добавлением, изменением и удалением наименований пива

Поля:
- ФИО
- Email
- Номер телефона
        
## User Stories

### BS-1 Как "Покупатель", я хочу получить список всех товаров, имеющихся в магазине с кратким описанием, и в результате получаю его

Request: 

`GET /api/beer/list`

Response: `200 OK`
```
[
    {
        "id": 1
        "type": "светлое",
        "in_stock": "true",
        "name": "Лидское",
        "description": "Лучшее пиво по бабушкиным рецептам",
        "alcohol": "5",
        "density": "11.5",
        "country": "Республика Беларусь",
        "price": "5"
    },
    {
        "id": 2,
        "type": "темное",
        "in_stock": "false",
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": "4.6",
        "density": "10.2",
        "country": "Республика Беларусь",
        "price": "3"
    },
    
]
```

### BS-2 Как "Покупатель", я хочу получить список товаров, отфильтрованных по критерию "Тип" (темное) , и в результате получаю список наименований темного пива

Request: 

`GET /api/beer/list?type=${beerType}`

`Headers: beerType="темное"`

Response: `200 OK`

``` 
[
    {
        "id": 2,
        "type": "темное",
        "in_stock": "false",
        "name": "Аливария",
        "description": "Пиво номер 1 в Беларуси",
        "alcohol": "4.6",
        "density": "10.2",
        "country": "Республика Беларусь",
        "price": "3"
    }
]
```

### BS-3 Как "Покупатель", хочу зарегистрироваться, и если пользователя с таким E-mail не найдено, регистрируюсь

Request: 
    
`POST /api/user/sign-up`
    
```    
{
    "customer": "Иван Иванов",
    "email": "ivan.ivanov@mail.ru",
    "password": 123456,
    "tel": "+375331234567"
}
```
Response: `201 CREATED`

```
{
   "id" : 1
}
```
### BS-4 Как "Покупатель", будучи зарегистрированным пользователем, я хочу войти в систему, и, если такой пользователь существует и пароль совпадает, войти в систему

Request: 
    
`POST /api/user/sign-in`
    
```    
{
    "email": "ivan.ivanov@mail.ru",
    "password": 123456
}
```
Response: `200 OK`

```
{
   "id" : 1
}
```

### BS-5 Как "Покупатель", я хочу выбрать, интресующие меня наименования пива с указанием объема, и оформить заказ, и если я авторизован, оформляю заказ

```
{
    "id": 1,
    "customer": "Иван Иванов",
    "email": "ivan.ivanov@mail.ru",
    "tel": "+375331234567",
    "processed": false,
    "total": 31,
    "order": [
        {
            "id": 2,
            "type": "темное",
            "name": "Аливария",
            "description": "Лучшее пиво по бабушкиным рецептам",
            "alcohol": "4.6",
            "density": "10.2",
            "country": "Пиво номер 1 в Беларуси",
            "price": "3",
            "volume": 5
        },
        {
            "id": 3,
            "type": "светлое осветлённое",
            "name": "Pilsner Urquell",
            "description": "непастеризованное",
            "alcohol": "4.2",
            "density": "12.0",
            "country": "Чехия",
            "price": "8",
            "volume": 2
        }
    ]
},
```

### BS-6 Как "Администратор", я хочу добавить новое наименование пива, и если такого наименования нет, добавляю его

Request: 
    
`POST /api/admin/beer/`
    
```    
{
    "type": "светлое осветлённое",
    "in_stock": "true",
    "name": "Pilsner Urquell",
    "description": "непастеризованное",
    "alcohol": "4.2",
    "density": "12.0",
    "country": "Чехия",
    "price": "8"
}
```

Response: `201 CREATED`

```
{
   "id" : 3
}
```
### BS-7 Как "Администратор", я хочу изменить цену пива, и если такого наименования есть, изменяю ему цену

Request: 
    
`PUT /api/admin/beer/${beerId}`

`Headers: beerId=3`
    
```    
{
    "price": "8.30"
}
```

Response: `200 OK`

```
{
    "id" : 3
}
```

### BS-8 Как "Администратор", я хочу удалить наименование пива, и если такое наименование есть, удаляю его

Request: 
    
`DELETE /api/admin/beer/${beerId}`
`Headers: beerId=3`

Response: `200 OK`


### BS-9 Как "Администратор", хочу получить список заказов, и получаю список с информацией по каждому заказу

Request: 
    
`GET /api/admin/orders/list`
    
Response: `200 OK`

```   
[
    {
        "id": 1,
        "customer": "Иван Иванов",
        "email": "ivan.ivanov@mail.ru",
        "tel": "+375331234567",
        "processed": true,
        "total": 31,
        "order": [
            {
                "id": 2,
                "type": "темное",
                "name": "Аливария",
                "description": "Лучшее пиво по бабушкиным рецептам",
                "alcohol": "4.6",
                "density": "10.2",
                "country": "Пиво номер 1 в Беларуси",
                "price": "3",
                "volume": 5
            },
            {
                "id": 3,
                "type": "светлое осветлённое",
                "name": "Pilsner Urquell",
                "description": "непастеризованное",
                "alcohol": "4.2",
                "density": "12.0",
                "country": "Чехия",
                "price": "8",
                "volume": 2
            }
        ]
    },
    {
        "id": 2,
        "customer": "Петр Петров",
        "email": "petr.petrovov@yandex.ru",
        "tel": "+375337654321",
        "processed": false,
        "total": 27,
        "order": [
            {
                "id": 2,
                "type": "темное",
                "name": "Аливария",
                "description": "Лучшее пиво по бабушкиным рецептам",
                "alcohol": "4.6",
                "density": "10.2",
                "country": "Республика Беларусь",
                "price": "3",
                "volume": 1
            },
            {
                "id": 3,
                "type": "светлое осветлённое",
                "name": "Pilsner Urquell",
                "description": "непастеризованное",
                "alcohol": "4.2",
                "density": "12.0",
                "country": "Чехия",
                "price": "8",
                "volume": 3
            }
        ]
    }
] 
```

### BS-10 Как "Администратор", я хочу изменить статус заказа на "Обработано", меняю его

Request: 
    
`PATCH /api/admin/orders/2`

```
{
    "processed": true
}
```
    
Response: `200 OK`
