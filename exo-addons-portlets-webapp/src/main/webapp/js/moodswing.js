$(function() {
    $( document ).ready(function(){
        var dNow = new Date();
        var monthNames = [ "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December" ];
        var dayNames = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        var localdate= (dayNames[dNow.getDay()] +', '+ monthNames[dNow.getMonth()] + ' ' + dNow.getDate() + ', ' + dNow.getFullYear());
        $('#current-date').text(localdate);


        $("#selectImage").imagepicker({
            hide_select: true,
            show_label: true
        });

        $('#selectImage').on('change', function() {
            $('#mood-swing').text(this.value);
        });

        var $container = $('.image_picker_selector');
        $container.imagesLoaded(function () {
            $container.masonry({
                columnWidth: 30,
                itemSelector: '.thumbnail'
            });
        });
    });
});

var toggleIconsPan = function(){
    ($("#selectSection").is(":visible"))
        ?( $("#selectSection").slideUp("slow"))
        :($("#selectSection").slideDown("slow"));
};

function loadProfile() {
    // Adding eXo Platform container information
    var opts = {};
    opts[opensocial.DataRequest.PeopleRequestFields.PROFILE_DETAILS] = [
        opensocial.Person.Field.PROFILE_URL,
        opensocial.Person.Field.THUMBNAIL_URL,
        "portalName",
        "restContext",
        "host"];
    var req = opensocial.newDataRequest();
    req.add(req.newFetchPersonRequest(opensocial.IdSpec.PersonId.VIEWER, opts), 'viewer');
    req.send(onLoadProfile);
}

