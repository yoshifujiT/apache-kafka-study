.PHONY: up
up: ## Starts any linked Services.
	docker-compose up -d

.PHONY: stop
package: ## Stop any linked Services.
	docker-compose stop

.PHONY: down
package: ## Down any linked Services.
	docker-compose down

### javaのpackaging

.PHONY: build
build:
	docker-compose exec java mvn package -DskipTests
