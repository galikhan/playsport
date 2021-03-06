//------------- main.js -------------//

// make console.log safe to use
window.console||(console={log:function(){}});

//Internet Explorer 10 in Windows 8 and Windows Phone 8 fix
if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
  var msViewportStyle = document.createElement('style')
  msViewportStyle.appendChild(
    document.createTextNode(
      '@-ms-viewport{width:auto!important}'
    )
  )
  document.querySelector('head').appendChild(msViewportStyle)
}

//Android stock browser
var nua = navigator.userAgent
var isAndroid = (nua.indexOf('Mozilla/5.0') > -1 && nua.indexOf('Android ') > -1 && nua.indexOf('AppleWebKit') > -1 && nua.indexOf('Chrome') === -1)
if (isAndroid) {
  $('select.form-control').removeClass('form-control').css('width', '100%')
}

//attach fast click
window.addEventListener('load', function() {
    FastClick.attach(document.body);
}, false);

//doc ready function
$(document).ready(function() {

    //------------- Fancy select -------------//
    $('.fancy-select').fancySelect();
    console.log('hello world of fancy');
    //custom templating
//    $('.fancy-select1').fancySelect({
//        optionTemplate: function(optionEl) {
//            return optionEl.text() + '<i class="pull-left ' + optionEl.data('icon') + '"></i>';
//        }
//    });

	//------------- Select 2 -------------//
//	$('.select2').select2({placeholder: 'Select state'});

//	//minumum 2 symbols input
//	$('.select2-minimum').select2({
//		placeholder: 'Select state',
//		minimumInputLength: 2,
//	});



    //Disable certain links
    $('a[href^=#]').click(function (e) {
        e.preventDefault()
    })

    //------------- Init our plugin -------------//
    $('body').dynamic({
        customScroll: {
            color: '#fff', //color of custom scroll
            rscolor: '#95A5A6', //color of right sidebar
            size: '3px', //size in pixels
            opacity: '1', //opacity
            alwaysVisible: false //disable hide in
        },
        header: {
            fixed: true //fixed header
        },
        breadcrumbs: {
            auto: true, //auto populate breadcrumbs via js if is false you need to provide own markup see for example.
            homeicon: 'im-home6' //home icon 
        },
        sidebar: {
            fixed: true,//fixed sidebar
            rememberToggle: true, //remember if sidebar is hided
            offCanvas: false //make sidebar offcanvas in tablet and small screens
        },
        rightSidebar: {
            fixed: true,//fixed sidebar
            rememberToggle: true//remember if sidebar is hided
        },
        sideNav : {
            toggleMode: true, //close previous open submenu before expand new
            showArrows: true,//show arrow to indicate sub
            sideNavArrowIcon: 'l-arrows-right', //arrow icon for navigation
            subOpenSpeed: 200,//animation speed for open subs
            subCloseSpeed: 300,//animation speed for close subs
            animationEasing: 'linear',//animation easing
            absoluteUrl: false, //put true if use absolute path links. example http://www.host.com/dashboard instead of /dashboard
            subDir: '' //if you put template in sub dir you need to fill here. example '/html'
        },
        panels: {
            refreshIcon: 'fa fa-circle-o',//refresh icon for panels
            toggleIcon: 'fa fa-angle-up',//toggle icon for panels
            collapseIcon: 'fa fa-angle-down',//colapse icon for panels
            closeIcon: 'fa fa-times', //close icon
            showControlsOnHover: false,//Show controls only on hover.
            loadingEffect: 'rotateplane',//loading effect for panels. bounce, none, rotateplane, stretch, orbit, roundBounce, win8, win8_linear, ios, facebook, rotation, pulse.
            loaderColor: '#616469',
            rememberSortablePosition: true //remember panel position
        },
        accordion: {
            toggleIcon: 'l-arrows-minus s16',//toggle icon for accrodion
            collapseIcon: 'l-arrows-plus s16'//collapse icon for accrodion
        },
        tables: {
            responsive: true, //make tables resposnive
            customscroll: true //ativate custom scroll for responsive tables
        },
        alerts: {
            animation: true, //animation effect toggle
            closeEffect: 'bounceOutDown' //close effect for alerts see http://daneden.github.io/animate.css/
        },
        dropdownMenu: {
            animation: true, //animation effect for dropdown
            openEffect: 'fadeIn',//open effect for menu see http://daneden.github.io/animate.css/
        }
    });

    //------------- Bootstrap tooltips -------------//
    $("[data-toggle=tooltip]").tooltip ({container:'body'});
    $(".tip").tooltip ({placement: 'top', container: 'body'});
    $(".tipR").tooltip ({placement: 'right', container: 'body'});
    $(".tipB").tooltip ({placement: 'bottom', container: 'body'});
    $(".tipL").tooltip ({placement: 'left', container: 'body'});
    //------------- Bootstrap popovers -------------//
    $("[data-toggle=popover]").popover ();


    //get plugin object 
    var adminObj = $('body').data('dynamic');
    //now we have access to change settings.

    //If new user set the localstorage variables
    if ( firstImpression() ) {
        //console.log('New user');
        if (adminObj.settings.header.fixed) {
            store.set('fixed-header', 1);
        } else {store.set('fixed-header', 0);}
        if (adminObj.settings.sidebar.fixed) {
            store.set('fixed-left-sidebar', 1);
        } else {store.set('fixed-left-sidebar', 0);}
        if (adminObj.settings.rightSidebar.fixed) {
            store.set('fixed-right-sidebar', 1);
        } else {store.set('fixed-right-sidebar', 0);}
    }

    //------------- Template Settings -------------//
    // (this is example , remove it in production state.)

    //checkbox states 
    // fixed header
    if (store.get('fixed-header') == 1 ) {
        $('#fixed-header-toggle').prop('checked', true);
    } else {
        $('#fixed-header-toggle').prop('checked', false);
    }

    //left sidebar
    if (store.get('fixed-left-sidebar') == 1 ) {
        $('#fixed-left-sidebar').prop('checked', true);
    } else {
        $('#fixed-left-sidebar').prop('checked', false);
    }

    //right sidebar
    if (store.get('fixed-right-sidebar') == 1 ) {
        $('#fixed-right-sidebar').prop('checked', true);
    } else {
        $('#fixed-right-sidebar').prop('checked', false);
    }

    //check magic access methods.
    $('#fixed-header-toggle').on('change', function () {
        if(this.checked) {
            adminObj.fixedHeader(true);
        } else {
            adminObj.fixedHeader(false);
        }
    });
    $('#fixed-left-sidebar').on('change', function () {
        if(this.checked) {
            adminObj.fixedSidebar('left');
        } else {
            adminObj.removeFixedSidebar('left');
        }
    });
    $('#fixed-right-sidebar').on('change', function () {
        if(this.checked) {
            adminObj.fixedSidebar('right');
        } else {
            adminObj.removeFixedSidebar('right');
        }
    });

    //    CUSTOM FUNCTIONS PLAYSPORT
    var removeUrl = "@{Manager.deleteImage()}";

    //lightbox enable on click image
    $('*[data-toggle="lightbox"]').click(function(event) {
        event.preventDefault();
        $(this).ekkoLightbox();
    });

    //timepicker
    $('.timepicker').timepicker({
    	upArrowStyle: 'fa fa-angle-up',
    	downArrowStyle: 'fa fa-angle-down',
    	minuteStep: 30,
    	showMeridian:false
    });

    ymaps.ready(init);
    var myMap;
    var initCoords;
    function init(){
        myMap = new ymaps.Map("map", {
            //Astana coords
            center: [51.1284,71.4306],
            zoom: 12
        });

        //var placemark = new ymaps.Placemark(myMap.getCenter(),{},{preset:"islands#redDotIcon"});
        //myMap.geoObjects.add(placemark);

        var placemarkSet = false;
        myMap.events.add('click', function(e){
            if(!placemarkSet){
                var clickedCoords = e.get("coords");
                document.getElementById("map-coordinates").value = clickedCoords[0]+","+clickedCoords[1];

                var placemark = new ymaps.Placemark(
                    clickedCoords,{},
                    {
                        preset:"islands#redDotIcon",
                        draggable:true,
                    }
                );
                placemark.events.add('dragend', function(event)
                {
                        var markerPosition = event.get("position");
                        // Переводим координаты страницы в глобальные пиксельные координаты.
                        var markerGlobalPosition = myMap.converter.pageToGlobal(markerPosition),
                            // Получаем центр карты в глобальных пиксельных координатах.
                            mapGlobalPixelCenter = myMap.getGlobalPixelCenter(),
                            // Получением размер контейнера карты на странице.
                            mapContainerSize = myMap.container.getSize(),
                            mapContainerHalfSize = [mapContainerSize[0] / 2, mapContainerSize[1] / 2],
                            // Вычисляем границы карты в глобальных пиксельных координатах.
                            mapGlobalPixelBounds = [
                                [mapGlobalPixelCenter[0] - mapContainerHalfSize[0], mapGlobalPixelCenter[1] - mapContainerHalfSize[1]],
                                [mapGlobalPixelCenter[0] + mapContainerHalfSize[0], mapGlobalPixelCenter[1] + mapContainerHalfSize[1]]
                            ];

                            var geoPosition = myMap.options.get('projection').fromGlobalPixels(markerGlobalPosition, myMap.getZoom());
                            document.getElementById("map-coordinates").value = geoPosition[0]+","+geoPosition[1];


                        // Проверяем, что завершение работы драггера произошло в видимой области карты.
//                        if (containsPoint(mapGlobalPixelBounds, markerGlobalPosition)) {
                            // Теперь переводим глобальные пиксельные координаты в геокоординаты с учетом текущего уровня масштабирования карты.
//                            var geoPosition = myMap.options.get('projection').fromGlobalPixels(markerGlobalPosition, myMap.getZoom());
//                            console.log('geopost', geoPosition);
//                        }
                        //console.log('global', markerGlobalPosition)
                });

                myMap.geoObjects.add(placemark);
                placemarkSet = true;
            }
        });
        console.log(myMap.geoObjects);

        showCityList();

        function drag(){
            console.log('drag');
        }

        function beforedrag(){
            console.log('before');
        }
    }
    function showCityList(){
        console.log("shoooow");
        $("#field-city-id").show();
    }

    function setCenter(){
        var city = document.getElementById("field-city-id").value;
        var code = document.getElementById("city-code-"+city).value;
        var array = code.split(",");
        var coord = [];
        coord.push(array[0]);
        coord.push(array[1]);
        console.log(coord);
        myMap.setCenter(coord);
    }


});
