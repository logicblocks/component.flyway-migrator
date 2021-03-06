(ns component.flyway-migrator.component
  (:require
   [com.stuartsierra.component :as component]

   [component.flyway-migrator.flyway :as flyway]))

(defrecord FlywayMigrator
           [data-source configuration]
  component/Lifecycle

  (start [component]
    (let [client
          (flyway/client
            (merge
              {:data-source data-source}
              configuration))
          migrate-on-start
          (get configuration :migrate-on-start true)]
      (when migrate-on-start
        (flyway/migrate client))
      (assoc component :client client)))

  (stop [component]
    (assoc component :client nil)))
