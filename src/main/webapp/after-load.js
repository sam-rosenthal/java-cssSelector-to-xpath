$(document).ready(function(){
  $('.sam-navigation').load("Navigation", function(){ 
	pageSpecificAfterLoad();
    $('[data-toggle="tooltip"]').tooltip();   
	});
});	