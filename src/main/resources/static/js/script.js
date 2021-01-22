$(document).ready(function () {

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

    function addCategory() {
        $.get("/recipes/create/addCategory").done(function (fragment) { // get from controller
            $("#new-input").replaceWith(fragment); // update snippet of page
        });
    }
    function submitForm() {
        document.recipe - form.reset();
    }

    if (window.location.href.indexOf("/home") > -1) {
        $("#home").hide();
    }

    if (window.location.href.indexOf("/recipes/all") > -1) {
        $("#search").hide();
        $('#all-recipes').hide();
    }

    if (window.location.href.indexOf("/login") > -1) {
        $("#login").hide();
        $("#search").hide();
    }

    if (window.location.href.indexOf("/register") > -1) {
        $("#register").hide();
        $("#search").hide();
    }

})