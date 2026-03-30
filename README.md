# Le New Black Wholesale Java SDK

Java 11+ SDK for the [Le New Black Wholesale v2 API](https://developer.lenewblack.com/docs/le-new-black-wholesale-api-v2).

## Installation

> Maven Central publishing is coming soon. In the meantime, install via [JitPack](https://jitpack.io).

**Gradle:**
```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("com.github.lenewblack:lnb-java:1.0.0")
}
```

**Maven:**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.github.lenewblack</groupId>
    <artifactId>lnb-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Quick Start

```java
import com.lenewblack.wholesale.LnbClient;
import com.lenewblack.wholesale.model.ResultSet;
import com.lenewblack.wholesale.model.Product;
import com.lenewblack.wholesale.param.ProductListParams;
import com.lenewblack.wholesale.param.ProductUpsertParams;
import com.lenewblack.wholesale.param.VariantUpdateParams;

LnbClient client = LnbClient.builder()
    .clientId("your_client_id")
    .clientSecret("your_client_secret")
    .build();

// List products (returns ResultSet)
ResultSet<Product> result = client.products().list(
    ProductListParams.builder().setCollectionCode("SS25").build()
);
for (Product product : result.getData()) {
    System.out.println(product.getModel() + ": " + product.getName());
}

// Auto-paginate through all products
for (Product product : client.products().paginate(
    ProductListParams.builder().setCollectionCode("SS25").build()
)) {
    System.out.println(product.getModel());
}

// Get a single product
Product product = client.products().get("MODEL-001");

// Create/update a product
ProductUpsertParams params = ProductUpsertParams.builder()
    .setModel("SS25-001")
    .setName("Silk Jacket")
    .setCategoryName("Jackets")
    .setCollectionCode("SS25")
    .setCollectionName("Spring Summer 2025")
    .setSizingName("EU Standard")
    .setVariants(List.of(
        VariantUpdateParams.builder()
            .setFabricCode("BLK-001")
            .setFabricName("Black Silk")
            .setReference("SS25-001-BLK")
            .setIsAvailable(true)
            .build()
    ))
    .build();
Product created = client.products().upsert(params);
```

## Authentication

Authentication is fully managed. The SDK automatically:
- Fetches a token on the first API call
- Caches the token for its validity period
- Refreshes the token before it expires (60s buffer)

## Resources

All API endpoints are organized into resources accessible from the client:

| Resource | Access | Methods |
|----------|--------|---------|
| Collections | `client.collections()` | `list`, `get` |
| Fabrics | `client.fabrics()` | `list`, `get` |
| Files | `client.files()` | `upload`, `delete` |
| Inventory | `client.inventory()` | `list`, `update`, `batchUpdate` |
| Invoices | `client.invoices()` | `list`, `get` |
| Orders | `client.orders()` | `list`, `get`, `upsert`, `paginate` |
| Prices | `client.prices()` | `list`, `update`, `batchUpdate` |
| Products | `client.products()` | `list`, `get`, `upsert`, `batchUpsert`, `paginate`, `getVariant`, `updateVariant`, `setVariantAlternatives` |
| Retailers | `client.retailers()` | `list`, `get` |
| Sales Catalogs | `client.salesCatalogs()` | `list`, `get` |
| Sales Documents | `client.salesDocuments()` | `list`, `get`, `updateStatus` |
| Selections | `client.selections()` | `list`, `get`, `create`, `delete`, `paginate` |
| Sizings | `client.sizings()` | `list`, `get` |

## Result Sets

All `list()` methods return a `ResultSet<T>` object:

```java
ResultSet<Product> result = client.products().list(
    ProductListParams.builder().setCollectionCode("SS25").build()
);

result.getData();                          // List<T>  — the items on this page
result.getMetadata().getPage();            // Integer  — current page number (1-based)
result.getMetadata().getPageSize();        // Integer  — items per page
result.getMetadata().isHasMore();          // Boolean  — whether more pages exist
result.getMetadata().getTotalPages();      // Integer  — total number of pages
result.getMetadata().getTotalItems();      // Integer  — total number of items
result.getMetadata().getFilters();         // Map<String, String> — applied filters
```

`ResultSet` implements `Iterable<T>`:

```java
for (Product product : result) { ... }  // iterate items directly
```

**Non-paginated endpoints** (Inventory, Prices, etc.) also return `ResultSet`, but with all pagination metadata set to `null`:

```java
ResultSet<Inventory> result = client.inventory().list(Map.of());
result.getMetadata().getPage();    // null — not a paginated endpoint
result.getMetadata().isHasMore();  // null
```

### Pagination metadata via response headers

Pagination metadata is populated from response headers sent by the API:

| Header | Property |
|--------|----------|
| `X-Pagination-Current-Page` | `getMetadata().getPage()` |
| `X-Pagination-Page-Size` | `getMetadata().getPageSize()` |
| `X-Pagination-Has-More` | `getMetadata().isHasMore()` |
| `X-Pagination-Total-Pages` | `getMetadata().getTotalPages()` |
| `X-Pagination-Total-Items` | `getMetadata().getTotalItems()` |

### Manual pagination

```java
ResultSet<Product> result = client.products().list(
    ProductListParams.builder().setPage(2).setCollectionCode("SS25").build()
);

System.out.println("Page: " + result.getMetadata().getPage());
System.out.println("Has more: " + result.getMetadata().isHasMore());
System.out.println("Total: " + result.getMetadata().getTotalItems()
    + " products across " + result.getMetadata().getTotalPages() + " pages");
```

### Auto-pagination (lazy iterator)

```java
for (Product product : client.products().paginate(
    ProductListParams.builder().setCollectionCode("SS25").build()
)) {
    // Automatically fetches next pages — memory efficient
}

// Stream-compatible:
StreamSupport.stream(
    client.products().paginate(ProductListParams.builder().build()).spliterator(), false
).filter(p -> p.isAvailable()).forEach(System.out::println);
```

## Batch Operations

Most resources support batch create/update:

```java
BatchResponse<Product> response = client.products().batchUpsert(List.of(params1, params2, params3));

System.out.println("Total: "   + response.getMetadata().getTotal());
System.out.println("Success: " + response.getMetadata().getSucceeded());
System.out.println("Errors: "  + response.getMetadata().getFailed());

for (BatchResult<Product> result : response.getResults()) {
    if (result.isSuccess()) {
        // result.getData() contains the created/updated entity
    } else {
        System.out.println("Error: " + result.getError());
    }
}
```

## Error Handling

```java
import com.lenewblack.wholesale.exception.AuthenticationException;
import com.lenewblack.wholesale.exception.NotFoundException;
import com.lenewblack.wholesale.exception.ValidationException;
import com.lenewblack.wholesale.exception.RateLimitException;

try {
    Product product = client.products().get("NONEXISTENT");
} catch (NotFoundException e) {
    System.out.println("Product not found: " + e.getMessage());
} catch (ValidationException e) {
    System.out.println("Validation error: " + e.getMessage());
    // e.getResponseBody() contains the full error response
} catch (AuthenticationException e) {
    System.out.println("Auth failed: " + e.getMessage());
} catch (RateLimitException e) {
    System.out.println("Rate limited — retry after: " + e.getRetryAfterSeconds() + "s");
}
```

All exceptions extend `ApiException` (unchecked):

```
RuntimeException
└── ApiException          (statusCode, responseBody)
    ├── AuthenticationException  (401)
    ├── NotFoundException        (404)
    ├── ValidationException      (422)
    └── RateLimitException       (429)
```

## Testing

```bash
./gradlew test
```

## Requirements

- Java 11+
- OkHttp >= 4.x
- Gson >= 2.x
