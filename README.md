AtlasMeat Platform

AtlasMeat is a full stack ordering and checkout platform built for a local meat processing business to manage
custom orders, pricing, and fulfillment through a modern web application.

The platform is designed to handle real operational workflows including order creation, validation, checkout
lifecycle management, and persistent storage with strong data integrity guarantees.

Development follows production style engineering practices with a focus on maintainability, testability, and
clear domain modeling.

Platform Goals
• Custom meat orders to be created and managed digitally
• Clear checkout states that reflect real business workflows
• Reliable data persistence and validation
• Consistent API responses for frontend and future integrations
• Scalable architecture that can grow with business needs

Technology Stack
Backend: Spring Boot, Java 17+, PostgreSQL, JUnit 5, Testcontainers
Frontend: Web client currently in development
Infrastructure and Tooling: Docker, Maven, Git with feature based development

Key Capabilities
• Order creation and modification tied to a checkout session
• Strict validation of API payloads with structured error responses
• Lifecycle enforcement for draft, submitted, and paid checkouts
• Conflict prevention for invalid state transitions
• Persistent storage across normalized relational tables
• Integration tests using real PostgreSQL instances

Architecture Overview
The backend follows a layered and domain focused design with controllers defining API contracts, services
enforcing business rules, repositories managing persistence, DTOs isolating payloads from internal models,
global exception handling for consistent responses, and integration tests validating real system behavior.

Current Functionality
• Create and manage checkout sessions
• Attach multiple orders to a checkout
• Validate required fields and business rules
• Prevent modification of finalized checkouts
• Return standardized API error objects
• Full database persistence

Actively In Development
• Frontend interface for business and customers
• Authentication and role based access
• Expanded order customization
• Reporting and analytics
• Deployment automation

Development Practices
• Feature branch workflow with incremental merges
• Readable commit history reflecting real engineering changes
• Integration first testing strategy
• Refactoring as domain complexity evolves
• Clear separation of concerns

Running the Backend
Java and Docker are required. The Spring Boot application runs locally and integration tests automatically
spin up PostgreSQL containers using Testcontainers.

Project Status
AtlasMeat is in active development and is being prepared for real world use. Ongoing changes reflect normal
platform growth including new features, refactors, and continuous improvements.