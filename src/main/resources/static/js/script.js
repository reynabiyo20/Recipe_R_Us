let ingredientsContainer = document.getElementById("ingredientsContainer");
let addIngredientButton = document.getElementById("addIngredientButton");
let instructionsContainer = document.getElementById("instructionsContainer");
let addInstructionButton = document.getElementById("addInstructionButton");

if(addIngredientButton) {
    addIngredientButton.addEventListener('click', (e) => {
        e.preventDefault();

        let newIngredient = document.createElement("input");
        newIngredient.setAttribute("name", "ingredient");
        newIngredient.setAttribute("placeholder", "Ingredient")
        ingredientsContainer.appendChild(newIngredient);

        let newQuantity = document.createElement("input");
        newQuantity.setAttribute("name", newQuantity);
        newQuantity.setAttribute("placeholder", "Quantity");
        ingredientsContainer.appendChild(newQuantity);
    })
}

if(addInstructionButton) {
    addInstructionButton.addEventListener('click', (e) => {
        e.preventDefault();
        let newInstruction = document.createElement("input");
        newInstruction.setAttribute("name", "instruction");
        instructionsContainer.appendChild(newInstruction);
    })
}