CREATE TABLE roles (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  rolname VARCHAR(100) NOT NULL
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(100) NOT NULL,
  email VARCHAR(150),
  password VARCHAR(255)
);

CREATE TABLE services (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  servicename VARCHAR(150),
  description TEXT,
  price DECIMAL(10,2),
  category VARCHAR(100),
  duration INT,
  active BOOLEAN,
  owner_id BIGINT,
  CONSTRAINT fk_service_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_ur_user FOREIGN KEY (user_id) REFERENCES users(id),
  CONSTRAINT fk_ur_role FOREIGN KEY (role_id) REFERENCES roles(id)
);