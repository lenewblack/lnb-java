# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2026-03-23

### Added
- Initial release of the Le New Black Java SDK
- `LnbClient` with fluent builder (`LnbClient.builder()`)
- `LnbClientBuilder` with configurable base URL, client credentials, and HTTP timeouts
- OAuth2 client credentials flow via `TokenManager` with automatic token refresh
- `ResultSet<T>` with pagination metadata (`page`, `perPage`, `total`, `lastPage`)
- `PageIterable<T>` / `PageIterator<T>` for auto-pagination (Iterable + Stream compatible)
- Resource classes: `ProductResource`, `OrderResource`, `CollectionResource`, `RetailerResource`, `FabricResource`, `SizingResource`, `InventoryResource`, `PriceResource`, `SalesDocumentResource`, `FileResource`, `SalesCatalogResource`, `InvoiceResource`, `SelectionResource`
- Model and param classes for all 13 resources
- Exception hierarchy: `LnbException`, `ApiException`, `AuthException`, `NetworkException`
- `RequestOptions` for per-request timeout overrides
- `VERSION` constant and `getVersion()` on `LnbClient`

[1.0.0]: https://github.com/lenewblack/lnb-java/releases/tag/v1.0.0
