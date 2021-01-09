let ingredientsContainer = document.getElementById("ingredientsContainer");
let addIngredientButton = document.getElementById("addIngredientButton");
let instructionsContainer = document.getElementById("instructionsContainer");
let addInstructionButton = document.getElementById("addInstructionButton");

if(addIngredientButton) {
    addIngredientButton.addEventListener('click', (e) => {
        e.preventDefault();

        let quantityEl = document.getElementById('quantity')
        let quanClone = quantityEl.cloneNode(true);
        quanClone.setAttributeNode('value', '')
        ingredientsContainer.appendChild(quanClone);

        let measurementEl = document.getElementById('measurement');
        let msrClone = measurementEl.cloneNode(true);
        msrClone.setAttributeNode('value', '')
        ingredientsContainer.appendChild(msrClone);

        let ingredientEl = document.getElementById('ingredient')
        let ingredClone = ingredientEl.cloneNode(true);
        ingredClone.setAttributeNode('value', '')
        ingredientsContainer.appendChild(ingredClone);
    })
}

if(addInstructionButton) {
    addInstructionButton.addEventListener('click', (e) => {
        e.preventDefault();
        let instructionEl = document.getElementById('instruction');
        let instructClone = instructionEl.cloneNode(true);
        instructClone.setAttribute('value', '')
        instructionsContainer.appendChild(instructClone);
    })
}