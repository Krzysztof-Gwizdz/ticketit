CREATE TABLE users (
                       user_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                       username VARCHAR(50) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(100) NOT NULL,
                       enabled TINYINT(1) DEFAULT 0,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       created_on TIMESTAMP,
                       CONSTRAINT `pk_users`
                           PRIMARY KEY (user_id),
                       CONSTRAINT `u_username`
                           UNIQUE (username),
                       CONSTRAINT `u_email`
                           UNIQUE (email)
) ENGINE = InnoDB;

CREATE TABLE authorities (
                             authority_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                             authority_name VARCHAR(50) NOT NULL,
                             CONSTRAINT `pk_authorities`
                                 PRIMARY KEY (authority_id),
                             CONSTRAINT `u_authority_name`
                                 UNIQUE (authority_name)
) ENGINE = InnoDB;

CREATE TABLE users_authorities (
                                   user_id INT UNSIGNED NOT NULL,
                                   authority_id INT UNSIGNED NOT NULL,
                                   CONSTRAINT `pk_users_authorities`
                                       PRIMARY KEY (user_id, authority_id),
                                   CONSTRAINT `fk_ua_users`
                                       FOREIGN KEY (user_id) REFERENCES users (user_id)
                                           ON UPDATE RESTRICT
                                           ON DELETE RESTRICT,
                                   CONSTRAINT `fk_ua_authorities`
                                       FOREIGN KEY (authority_id) REFERENCES authorities (authority_id)
                                           ON DELETE RESTRICT
                                           ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE UNIQUE INDEX ix_auth_userid
	ON users_authorities (user_id, authority_id);

CREATE TABLE projects (
                          project_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          acronym VARCHAR(50) NOT NULL,
                          description TEXT,
                          created_on TIMESTAMP,
                          CONSTRAINT `pk_projects`
                              PRIMARY KEY (project_id),
                          CONSTRAINT `u_project_acronym`
                              UNIQUE (acronym)
) ENGINE = InnoDB;

CREATE TABLE project_roles (
                               role_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                               role_name VARCHAR(255) NOT NULL,
                               CONSTRAINT `pk_projects`
                                   PRIMARY KEY (role_id)
) ENGINE = InnoDB;

CREATE TABLE project_users (
                               project_id INT UNSIGNED NOT NULL,
                               user_id INT UNSIGNED NOT NULL,
                               role_id INT UNSIGNED NOT NULL,
                               joined_on TIMESTAMP,
                               CONSTRAINT `pk_users_projects`
                                   PRIMARY KEY (project_id, user_id, role_id),
                               CONSTRAINT `fk_pu_projects`
                                   FOREIGN KEY (project_id) REFERENCES projects (project_id)
                                       ON DELETE RESTRICT
                                       ON UPDATE RESTRICT,
                               CONSTRAINT `fk_pu_users`
                                   FOREIGN KEY (user_id) REFERENCES users (user_id)
                                       ON DELETE RESTRICT
                                       ON UPDATE RESTRICT,
                               CONSTRAINT `fk_pu_roles`
                                   FOREIGN KEY (role_id) REFERENCES project_roles (role_id)
                                       ON DELETE RESTRICT
                                       ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE UNIQUE INDEX ix_prj_userid
	ON project_users (project_id, user_id, role_id);

CREATE TABLE ticket_statuses (
                                 status_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                 status_name VARCHAR(50) NOT NULL,
                                 CONSTRAINT `pk_ticket_status`
                                     PRIMARY KEY (status_id),
                                 CONSTRAINT `u_status_name`
                                     UNIQUE (status_name)
) ENGINE = InnoDB;

CREATE TABLE tickets (
                         ticket_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                         user_id INT UNSIGNED NOT NULL,
                         project_id INT UNSIGNED NOT NULL,
                         status_id INT UNSIGNED NOT NULL,
                         title VARCHAR(100) NOT NULL,
                         content TEXT,
                         created_on TIMESTAMP,
                         modified_on TIMESTAMP,
                         CONSTRAINT `pk_tickets`
                             PRIMARY KEY (ticket_id),
                         CONSTRAINT `fk_ticket_user`
                             FOREIGN KEY (user_id) REFERENCES users (user_id)
                                 ON DELETE RESTRICT
                                 ON UPDATE RESTRICT,
                         CONSTRAINT `fk_ticket_project`
                             FOREIGN KEY (project_id) REFERENCES projects (project_id)
                                 ON DELETE RESTRICT
                                 ON UPDATE RESTRICT,
                         CONSTRAINT `fk_ticket_status`
                             FOREIGN KEY (status_id) REFERENCES ticket_statuses (status_id)
                                 ON DELETE RESTRICT
                                 ON UPDATE RESTRICT
) ENGINE = InnoDB;

CREATE TABLE ticket_comments (
                                 comment_id INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                 ticket_id INT UNSIGNED NOT NULL,
                                 user_id INT UNSIGNED NOT NULL,
                                 content TEXT,
                                 created_on TIMESTAMP,
                                 CONSTRAINT `pk_ticket_comments`
                                     PRIMARY KEY (comment_id),
                                 CONSTRAINT `fk_ticketcom_ticket`
                                     FOREIGN KEY (ticket_id) REFERENCES tickets (ticket_id),
                                 CONSTRAINT `fk_ticketcom_user`
                                     FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE = InnoDB;