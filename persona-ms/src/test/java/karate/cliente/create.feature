Feature: Crear nuevo cliente, consultar y eliminar

  Background:
    * url baseUrl

  Scenario: Crear un nuevo cliente

    Given path '/clientes/crear'
    And request { edad:15, contrasena:"1234", direccion:"Av. aaa", dni:"84011345", genero:"M", nombre:"Nombre", telefono:"912912912" }
    And header Accept = 'application/json'
    When method post
    Then status 201
    And match response == 
    """
        {
          "dni": "#string",
          "nombre": "#string",
          "genero": "#string",
          "edad": #number,
          "direccion": "#string",
          "telefono": "#string",
          "clienteId": "#string",
          "estado": #boolean
        }
    """
    And match response.dni == '84011345'
    And match response.nombre == 'Nombre'
    And match response.genero == 'M'
    And match response.edad == 15
    And match response.direccion == 'Av. aaa'
    And match response.telefono == '912912912'
    And match response.estado == true

    Scenario: Consultar cliente por DNI existente
    Given path '/clientes/84011345'
    When method GET
    Then status 200
    And match response == 
    """
        {
          "dni": "84011345",
          "nombre": "Nombre",
          "genero": "M",
          "edad": 15,
          "direccion": "Av. aaa",
          "telefono": "912912912",
          "clienteId": "#string",
          "estado": true
        }
    """

    Scenario: Eliminar cliente por DNI existente
    Given path '/clientes/84011345'
    When method DELETE
    Then status 200
