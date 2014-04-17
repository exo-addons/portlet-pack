(function($) {
  $(document).ready(function(){

    var pieData = [
      {
        value: 33,
        color:"#4581a5"
      },
      {
        value : 30,
        color : "#2eb87c"
      },
      {
        value : 20,
        color : "#f47b5e"
      },
      {
        value : 10,
        color : "#f3b665"
      }

    ];

    var options = {
      animation : false
    }
    var moodyCanvas = document.getElementById("moody-canvas");
    if (moodyCanvas !== null) {
      var myPie = new Chart(moodyCanvas.getContext("2d")).Pie(pieData, options);
    }

  });

})(jqchat);
