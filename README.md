# Price-Comparator-Market

I dont have time, sorry :(

This is the backend service for Price Comparator, an application that helps users monitor shopping baskets, optimize cost savings, and track discounts across multiple stores.

I implement the Daily Shopping Basket Monitoring, Best Discounts and New Discounts tasks.

## Getting started

git clone https://github.com/KerekesBalazs/Price-Comparator-Market
cd Price-Comparator-Market

docker-compose up -d

./gradlew bootRun

## Arhitecture

I try to follow the principles of Domain-Driven Design.

## Api

Base api is /api.

## Endpoints

### 1. Upload Resources

Upload CSV files containing product prices or discount data.

#### Upload Product Prices

- **URL:** `/resources/product-prices/upload`
- **Method:** `POST`
- **Request:** Multipart form-data with a file parameter named `file` (CSV format)
- **Response:**
    - `200 OK` — File processed successfully
    - `400 Bad Request` — Invalid or non-CSV file uploaded
    - `500 Internal Server Error` — Error while parsing the CSV file

#### Upload Discounts

- **URL:** `/resources/discounts/upload`
- **Method:** `POST`
- **Request:** Multipart form-data with a file parameter named `file` (CSV format)
- **Response:**
    - `200 OK` — File processed successfully
    - `400 Bad Request` — Invalid or non-CSV file uploaded
    - `500 Internal Server Error` — Error while parsing the CSV file

---

### 2. Discounts

Retrieve discount information with filtering options.

#### Get Best Discounts

- **URL:** `/discounts/best`
- **Method:** `GET`
- **Description:** Returns a list of all products with their highest currently active discount across all tracked stores.
- **Response:**
    - `200 OK` — List of products and their maximum discounts in JSON format
    - `500 Internal Server Error` — If an error occurs

- **Response Body:** Array of objects containing:
    - `product` — Product details (as DTO)
    - `discount` — Discount details (as DTO)


## GET /discounts/new

Retrieve a list of discounts that are newly added since a specified date.

### Request

- **Method:** `GET`
- **URL:** `/discounts/new`
- **Query Parameters:**
    - `date` (optional) — A date in ISO format (`YYYY-MM-DD`). If provided, the endpoint returns discounts added on or after this date.  
      If omitted, the default behavior is to retrieve discounts added in the last 24 hours.

### Response

- **Success (200 OK):**  
  Returns a JSON array of `GetDiscountDTO` objects representing the newly added discounts.

- **Error (500 Internal Server Error):**  
  Returned if an exception occurs during processing.

### Example Request

GET /discounts/new?date=2025-05-24



### POST /optimize

Optimize a basket of products based on a given list of product IDs.

#### Request

- **Method:** `POST`
- **URL:** `/api/baskets/optimize`
- **Body:** JSON array of product IDs (`List<Long>`)

Example:
```json
[101, 202, 303]