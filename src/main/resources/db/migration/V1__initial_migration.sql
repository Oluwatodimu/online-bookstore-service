CREATE TABLE app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_by VARCHAR(255),
    creation_date DATETIME,
    last_modified_by VARCHAR(255),
    last_modified_date DATETIME
);

CREATE TABLE authority (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    authority_name VARCHAR(64) NOT NULL,
    created_by VARCHAR(255),
    creation_date DATETIME,
    last_modified_by VARCHAR(255),
    last_modified_date DATETIME
);


CREATE TABLE user_authority (
    user_id BIGINT NOT NULL,
    authority_id BIGINT NOT NULL,

    PRIMARY KEY(user_id, authority_id)
);
