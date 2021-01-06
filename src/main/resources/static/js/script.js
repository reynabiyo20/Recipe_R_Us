let ingredientsContainer = document.getElementById("ingredientsContainer");
let addIngredientButton = document.getElementById("addIngredientButton");
let instructionsContainer = document.getElementById("instructionsContainer");
let addInstructionButton = document.getElementById("addInstructionButton");

if(addIngredientButton) {
    addIngredientButton.addEventListener('click', (e) => {
        e.preventDefault();
        let newIngredient = document.createElement("input");
        newIngredient.setAttribute("name", "ingredient");
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