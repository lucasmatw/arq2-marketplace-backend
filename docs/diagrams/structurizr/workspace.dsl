workspace {

    model {
        user = person "User" "Un usuario de Mercadoflux." "Customer"

        group "Mercadoflux Group" {
            mercadoflux = softwaresystem "Mercadoflux" "" {

                flux_app = container "Mercadoflux App" "REST API" "Spring WebFlux" "Internal System" {

                    group "Controllers (Puertos de entrada)" {
                        user_controller = component "UserController" "Controller" "Spring WebFlux Controller" "Controller"
                        product_controller = component "ProductController" "Controller" "Spring WebFlux Controller" "Controller"
                        purchase_controller = component "PurchaseController" "Controller" "Spring WebFlux Controller" "Controller"
                    }

                    group "Services (implementan los casos de uso)" {
                        user_service = component "UserService" "Service" "User Service" "Internal System"
                        auth_user_service = component "AuthUserService" "Service" "Auth User Service" "Internal System"
                        product_service = component "ProductService" "Service" "Product Service" "Internal System"
                        purchase_service = component "PurchaseService" "Service" "Purchase Service" "Internal System"
                        search_service = component "SearchProductService" "Service" "Search Product Service" "Internal System"
                    }

                    group "Services (auxiliares)" {
                        date_service = component "DateService" "Service" "Dates Service" "Internal System"
                        money_service = component "MoneyAccountService" "Service" "Money Account Service" "Internal System"
                    }

                    group "Repositories (Puertos de salida)" {
                        user_repository = component "UserRepository" "Repository" "User Repository" "Repository"
                        product_repository = component "ProductRepository" "Repository" "Product Repository" "Repository"
                        purchase_repository = component "PurchaseRepository" "Repository" "Purchase Repository" "Repository"
                        money_repository = component "MoneyAccountRepository" "Repository" "Money Account Repository" "Repository"
                    }

                }
                database = container "Mongo Atlas" "MongoDB en la nube" "Database" "Database"

            }
        }

       user -> flux_app "Usa"
       flux_app -> database "Almacena datos en"

       user_repository -> database "Almacena datos en"

       user_controller -> user_service "Usa"
       user_controller -> auth_user_service "Usa"
       user_service -> user_repository "Usa"
       user_service -> money_service "Usa"

       auth_user_service -> user_service "Usa"
       auth_user_service -> product_service "Usa"

       product_controller -> product_service "Usa"
       product_controller -> search_service "Usa"
       product_service -> product_repository "Usa"
       product_repository -> database "Almacena datos en"

       purchase_controller -> purchase_service "Usa"
       purchase_service -> purchase_repository "Usa"
       purchase_service -> money_service "Usa"
       purchase_service -> date_service "Usa"
       purchase_repository -> database "Almacena datos en"

       search_service -> product_repository "Usa"

       money_service -> money_repository "Usa"
       money_repository -> database "Almacena datos en"
    }

    views {
        systemcontext mercadoflux "SystemContext" {
            include *
            autoLayout
        }

        container mercadoflux "Containers" {
            include *
            autoLayout
            description "Contenedores del sistema Mercadoflux"
        }

        component flux_app "Components" {
            include *
            autoLayout
            description "Componentes del sistema Mercadoflux"
        }

        styles {
                    element "Person" {
                        color #ffffff
                        fontSize 22
                        shape Person
                    }
                    element "Customer" {
                        background #08427b
                    }
                    element "Bank Staff" {
                        background #999999
                    }
                    element "Software System" {
                        background #1168bd
                        color #ffffff
                    }
                    element "External System" {
                        background #999999
                        color #ffffff
                    }
                    element "Internal System" {
                        background #438dd5
                        color #ffffff
                    }
                    element "External System" {
                        background #a1abb5
                        color #ffffff
                    }
                    element "Web Browser" {
                        shape WebBrowser
                    }
                    element "Mobile App" {
                        shape MobileDeviceLandscape
                    }
                    element "Database" {
                        background #a1abb5
                        shape Cylinder
                    }
                    element "Component" {
                        background #85bbf0
                        color #000000
                    }
                    element "Repository" {
                        background #064a8f
                        color #ffffff
                    }

                    element "Failover" {
                        opacity 25
                    }
                }
    }

}