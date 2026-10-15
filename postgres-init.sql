-- Runs once, the first time the Postgres container starts with an empty volume.
-- notification-service has no database, so only 4 are needed.
CREATE DATABASE user_service_db;
CREATE DATABASE restaurant_service_db;
CREATE DATABASE order_service_db;
CREATE DATABASE payment_service_db;
