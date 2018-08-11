function adjustTopOfCard(){
	if(screen.width<=1280){
		var newTop= 30+$(".sam-navigation").height(); 
		$('.sambox').css('margin-left',30);
		$('.sambox').css('margin-right',30);
	}
	else{
		var newTop= 60+$(".sam-navigation").height();
		$('.sambox').css('margin-left',60);
		$('.sambox').css('margin-right',60);
	} 
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