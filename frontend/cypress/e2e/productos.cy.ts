/// <reference types="cypress" />

describe("Pruebas E2E - Módulo de Productos (MiniStock Cloud)", () => {
  beforeEach(() => {
    // 1. Visitar la pantalla de login
    cy.visit("http://localhost:5173");
    cy.wait(1500); // Pausa para observar el login cargado

    // 2. Digitar credenciales y hacer clic
    cy.get('input[type="email"]').type("cj60sebastian@gmail.com");
    cy.wait(1000);
    cy.get('input[type="password"]').type("naruto147");
    cy.wait(1000);
    cy.contains("button", "Iniciar Sesión").click();
    cy.wait(2000); // Pausa para ver la transición al dashboard

    // 3. Navegar a Productos
    cy.contains("Productos").click();
    cy.wait(1500);
  });

  it("Debe registrar un nuevo producto y mostrarlo en la lista", () => {
    // 4. Llenar cada campo de manera pausada y visible
    cy.get('input[placeholder*="P003"]').type("P099");
    cy.wait(1000);

    cy.get('input[placeholder*="Aceite"]').type("Aceite de Oliva 500ml");
    cy.wait(1000);

    cy.get('input[placeholder*="15000"]').type("18500");
    cy.wait(1000);

    cy.get('input[placeholder*="http"]').type(
      "https://cdn-icons-png.flaticon.com/512/3081/3081559.png",
    );
    cy.wait(1500);

    // 5. Clic en guardar
    cy.contains("button", "Guardar Producto").click();
    cy.wait(2000); // Pausa para ver el renderizado en la base de datos y React

    // 6. Validaciones
    cy.contains("Aceite de Oliva 500ml").should("be.visible");
    cy.contains("Cód: P099").should("be.visible");
  });
});
