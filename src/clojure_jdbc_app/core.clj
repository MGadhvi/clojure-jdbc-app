(ns clojure-jdbc-app.core
  (:require [next.jdbc :as jdbc]
            [clojure.edn :as edn]
            [next.jdbc.sql :as sql]
            [clojure.java.io :as io]))

(defn load-config []
  (with-open [rdr (io/reader "resources/config.edn")]
    (edn/read rdr)))

(def config (load-config))
(def db-spec (:db-spec config))

;; Function to create a new user
(defn create-user [name email]
  (jdbc/execute! db-spec
                 ["INSERT INTO users (name, email, created_at) VALUES (?, ?, CURRENT_TIMESTAMP)"
                  name email]))

;; Function to retrieve all users
(defn get-users []
  (jdbc/execute! db-spec ["SELECT * FROM users"]))

;; Function to find a user by email
(defn find-user-by-email [email]
  (jdbc/execute! db-spec ["SELECT * FROM users WHERE email = ?" email]))

;; Main function to demonstrate usage
(defn -main []
  (create-user "John Doe" "john@example.com")
  (println "All Users:" (get-users))
  (println "Find User by Email:" (find-user-by-email "john@example.com")))
