function adjustTopOfCard(){
	var newTop= 60+$(".sam-navigation").height(); 
	$('.sambox').css('top',newTop);
}
$(document).ready(function(){
  $('.sam-navigation').load("Navigation", function(){ 
	  	$('.sam-card').load("Card",function(){
	  		adjustTopOfCard();
	  		pageSpecificAfterLoad();
	  	});
		$( window ).resize(function() {
		  	adjustTopOfCard();
		});
	    $('[data-toggle="tooltip"]').tooltip();  
	    $('#sam-navbar').on('hidden.bs.collapse', function () {
		  	adjustTopOfCard();
		}); 
	    $('#sam-navbar').on('shown.bs.collapse', function () {
		  	adjustTopOfCard();
		});
   });
});	