# API

## Получение всех новостей  
Глагол: `GET`  
Url: `https://rusha-news-server.herokuapp.com/`  
Ответ (JSON):  
`id: Int`  
`creationTime: String`  
`title: String`  
`text: String`  

## Добавление новости в начало
Глагол: `POST`  
Url: `https://rusha-news-server.herokuapp.com/add`  
Тело (`@Body` запроса, JSON):  
title: String  
text: String  

## Обновить новость:
Глагол: `PUT`  
Url: `https://rusha-news-server.herokuapp.com/<id новости>`  
Тело (`@Body` запроса, JSON):  
`title: String`  
`text: String`  

## Удалить новость:
Глагол: `DELETE`  
Url: `https://rusha-news-server.herokuapp.com/<id новости>`  