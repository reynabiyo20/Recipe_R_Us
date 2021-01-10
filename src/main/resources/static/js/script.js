$(document).ready(function () {

    const instructionsContainer = document.getElementById("instructionsContainer");
    const addInstructionButton = document.getElementById("addInstructionButton");


//add ingredient on add ingredent button click
    if($('#addIngredientButton')) {
        $('#addIngredientButton').on('click', function(e){
            e.preventDefault();
              $('#recipeIngredient').clone().find("input").val("").end().appendTo('#ingredientsContainer');
          });

// Remove Ingredient on clicking the red x, if not first ingredient
       $('#ingredientsContainer').on('click', 'i', function(e){
            if($(this).parent().parent().index() > 0) {
                $(this).parent().parent().remove();
            }
        })
    }

//
    if($('#addInstructionButton')) {
        $('#addInstructionButton').on('click', function(e){
            e.preventDefault();
            $('#recipeInstruction').clone().find("input").val("").end().appendTo('#instructionsContainer');
        })

// Remove Instruction on clicking the red x, if not first ingredient
       $('#instructionsContainer').on('click', 'i', function(e){
            if($(this).parent().parent().index() > 0) {
                $(this).parent().parent().remove();
            }
        })
    }
})