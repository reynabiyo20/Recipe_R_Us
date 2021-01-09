let ingredientsContainer = document.getElementById("ingredientsContainer");
let addIngredientButton = document.getElementById("addIngredientButton");
let instructionsContainer = document.getElementById("instructionsContainer");
let addInstructionButton = document.getElementById("addInstructionButton");

if(addIngredientButton) {
    addIngredientButton.addEventListener('click', (e) => {
        e.preventDefault();

        let newQuantity = document.createElement("input");
        newQuantity.setAttribute("name", "quantity");
        newQuantity.setAttribute('id', 'quantity')
        newQuantity.setAttribute('type', 'number');
        newQuantity.setAttribute('min', '1');
        newQuantity.setAttribute('step', '.1')
        newQuantity.setAttribute("placeholder", "Quantity")
        newQuantity.required = true;
        ingredientsContainer.appendChild(newQuantity);



        let measurementEl = document.getElementById('measurement');
        let msrClone = measurementEl.cloneNode(true);
        ingredientsContainer.appendChild(msrClone);

        let newIngredient = document.createElement("input");
        newIngredient.setAttribute("name", "ingredient");
        newIngredient.setAttribute('id', 'ingredient')
        newIngredient.setAttribute('type', 'text');
        newIngredient.setAttribute("placeholder", "Ingredient")
        newIngredient.required = true;
        ingredientsContainer.appendChild(newIngredient);

    })
}

if(addInstructionButton) {
    addInstructionButton.addEventListener('click', (e) => {
        e.preventDefault();
        let newInstruction = document.createElement('input');
        newInstruction.setAttribute("name", "instruction");
        newInstruction.setAttribute('id', 'instruction')
        newInstruction.setAttribute('type', 'text');
        newInstruction.setAttribute("placeholder", "Instruction")
        newInstruction.required = true;
        instructionsContainer.appendChild(newInstruction);
    })
}