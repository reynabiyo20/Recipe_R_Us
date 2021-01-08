let ingredientsContainer = document.getElementById("ingredientsContainer");
let addIngredientButton = document.getElementById("addIngredientButton");
let instructionsContainer = document.getElementById("instructionsContainer");
let addInstructionButton = document.getElementById("addInstructionButton");

if(addIngredientButton) {
    addIngredientButton.addEventListener('click', (e) => {
        e.preventDefault();

        let newQuantity = document.createElement("input");
        newQuantity.setAttribute("name", "quantity");
        newQuantity.setAttribute("placeholder", "Quantity");
        ingredientsContainer.appendChild(newQuantity);

        let measurementEl = document.getElementById('measurement');
        let msrClone = measurementEl.cloneNode(true);
        console.log(msrClone)
        ingredientsContainer.appendChild(msrClone);

        let newIngredient = document.createElement("input");
        newIngredient.setAttribute("name", "ingredient");
        newIngredient.setAttribute("placeholder", "Ingredient")
        ingredientsContainer.appendChild(newIngredient);
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