if ("object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.addresssearchfields = function($, ko) {
  /**
   * @return {undefined}
   */
  function init() {
    var emptyJ = $("#address_search_address");
    new lotplan.utils.TypeaheadWrapper(emptyJ, function(m3, dataAndEvents, unique) {
      self.loading(true);
      /** @type {string} */
      var url = baseUrl + encodeURIComponent("LOWER(ADDRESS) like '" + m3.toLowerCase().trim() + "%'");
      return $.get(url).success(function(output) {
        self.loading(false);
        /** @type {Array} */
        var elems = [];
        return output = JSON.parse(output), output.features.length < 1 ? (self.addressValid(false), false) : (output.features.forEach(function(model, dataAndEvents) {
          if ($.inArray(model.attributes.ADDRESS, elems) < 0) {
            elems.push(model.attributes.ADDRESS);
            self.addressValid(true);
          }
        }), void unique(elems));
      });
    });
    $("#address_search_address").on("typeahead:selected", function(dataAndEvents, deepDataAndEvents) {
      $("#address_search_address").closest("form").submit();
    });
    $(document).on("loadingFinished", false, function(dataAndEvents) {
      self.loading(false);
    });
  }
  /**
   * @param {?} loadingLang
   * @return {undefined}
   */
  function load(loadingLang) {
    self = this;
    self.loading = ko.observable(false);
    self.addressValid = ko.observable(false);
    self.isError = ko.observable(false);
    /** @type {function (?, ?): undefined} */
    self.makeAddressSearch = render;
    /** @type {function (?, Event): undefined} */
    self.resetAddressSearch = handler;
    self.searchText = ko.observable("");
  }
  /**
   * @param {?} context
   * @param {?} rows
   * @return {undefined}
   */
  function render(context, rows) {
    self.loading(true);
    this.isError(false);
    lotplan.main.clearData();
    self.searchText($(context).find("#address_search_address").val());
    find($(context).find("#address_search_address").val());
  }
  /**
   * @param {?} token
   * @param {Event} ev
   * @return {undefined}
   */
  function handler(token, ev) {
    $(ev.target).closest("form").find('input[type="text"]').val("");
    this.isError(false);
    lotplan.main.clearData();
  }
  /**
   * @param {string} seed
   * @return {undefined}
   */
  function find(seed) {
    lotplan.utils.ajaxRequest(deliminator + encodeURIComponent("ADDRESS='" + seed + "'"), "GET", null, lotplan.components.searchresults.setSearchData.bind(self));
  }
  var self;
  /** @type {string} */
  var baseUrl = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/0/query?geometryType=esriGeometryPoint&spatialRel=esriSpatialRelIntersects&returnCountOnly=false&returnIdsOnly=false&returnGeometry=false&outFields=ADDRESS&f=json&where=";
  /** @type {string} */
  var deliminator = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/0/query?text=&geometryType=esriGeometryPoint&spatialRel=esriSpatialRelIntersects&returnCountOnly=false&returnIdsOnly=false&returnGeometry=true&outFields=*&f=json&where=";
  return{
    /** @type {function (): undefined} */
    setup : init,
    /**
     * @param {?} err
     * @return {undefined}
     */
    setError : function(err) {
      self.isError(err);
    },
    /** @type {function (?): undefined} */
    viewModel : load,
    template : {
      element : "addressSearchFields"
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.review = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
  }
  /**
   * @param {?} state
   * @return {undefined}
   */
  function State(state) {
    scope = this;
    scope.selectedAddress = ko.observableArray(state.selectedAddress());
    /** @type {function (?, ?): undefined} */
    scope.cancel = cb;
    /** @type {function (?, Event): undefined} */
    scope.addToCart = add;
    /** @type {function (?, Event): undefined} */
    scope.goToCart = success;
    scope.added = ko.observable();
  }
  /**
   * @param {?} stats
   * @param {?} stdout
   * @return {undefined}
   */
  function cb(stats, stdout) {
    /** @type {string} */
    window.location.hash = "page=search";
  }
  /**
   * @param {?} vec0
   * @param {Event} e
   * @return {undefined}
   */
  function add(vec0, e) {
    $(e.target).attr("disabled", "disabled");
    lotplan.main.addToBasket(scope.selectedAddress()[0]);
    lotplan.utils.setCookie("basket", JSON.stringify(scope.selectedAddress()[0]), 60);
    scope.added(true);
  }
  /**
   * @param {?} textStatus
   * @param {Event} data
   * @return {undefined}
   */
  function success(textStatus, data) {
    $(data.target).attr("disabled", "disabled");
    /** @type {string} */
    window.location.hash = "page=cart";
  }
  var scope;
  return{
    /** @type {function (): undefined} */
    setup : setup,
    /** @type {function (?): undefined} */
    viewModel : State,
    template : {
      element : "basketSummary"
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.cart = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
  }
  /**
   * @param {?} user
   * @return {undefined}
   */
  function setUser(user) {
    self = this;
    self.removeItems = ko.observableArray();
    /** @type {function (): undefined} */
    self.removeSelectedItems = dispose;
    self.givenName = ko.observable();
    self.familyName = ko.observable();
    self.email = ko.observable();
    self.confirmEmail = ko.observable();
    self.phone = ko.observable();
    self.accept_tc = ko.observable(false);
    self.opt_in = ko.observable(false);
    /** @type {function (): ?} */
    self.validateBuy = add;
    /** @type {function (): undefined} */
    self.buyProcess = refresh;
    self.alertType = ko.observable("");
    self.alertMessage = ko.observable("");
    /**
     * @return {undefined}
     */
    self.alertClose = function() {
      self.alertType("");
      self.alertMessage("");
    };
  }
  /**
   * @return {?}
   */
  function add() {
    return(new lotplan.utils.Validator).add("Enter your given name", function() {
      return this.givenName();
    }).add("Enter your family name", function() {
      return this.familyName();
    }).add("Enter your email address", function() {
      return this.email();
    }).add("Enter your phone number", function() {
      return this.phone();
    }).add("Confirmation email must be the same as email address", function() {
      return this.email() === this.confirmEmail();
    }).add("You must accept the terms, conditions and privacy statement to continue", function() {
      return this.accept_tc();
    }).validate(self, function(dataAndEvents, left) {
      return!!dataAndEvents || (self.alertType("danger"), self.alertMessage(this.errorMessage("<p>The following " + (left > 1 ? "are" : "is") + " required to complete your purchase</p>")), false);
    });
  }
  /**
   * @return {undefined}
   */
  function dispose() {
    /** @type {number} */
    var i = 0;
    for (;i < self.removeItems().length;i++) {
      lotplan.main.cartHelper().remove(self.removeItems()[i]);
    }
    self.removeItems.removeAll();
  }
  /**
   * @return {undefined}
   */
  function refresh() {
    if (confirm("You are about to be sent to our payment service provided by the Commonwealth Bank. Once the payment is confirmed, you will be returned to our website. \n\nClick OK to continue or cancel if you aren't ready to pay yet.")) {
      var codeSegments = lotplan.main.cartHelper().items();
      /** @type {Array} */
      var entries = [];
      /** @type {number} */
      var i = 0;
      for (;i < codeSegments.length;i++) {
        entries.push({
          product_id : codeSegments[i].i,
          title_reference : codeSegments[i].t,
          plan_type : codeSegments[i].pt,
          plan_number : codeSegments[i].pn,
          lot : codeSegments[i].l
        });
      }
      var data = {
        first_name : self.givenName(),
        last_name : self.familyName(),
        phone : self.phone(),
        email : self.email(),
        opt_in : self.opt_in() ? 1 : 0,
        entries : entries
      };
      lotplan.utils.ajaxRequest(udataCur, "POST", data, function(b) {
        if (b) {
          /** @type {string} */
          window.location.href = "https://www.bpoint.com.au/" + b;
        }
      }, function(dataAndEvents) {
        self.alertType("danger");
        self.alertMessage("<p>There was a problem with the request, please check that your details are correct.</p>");
      });
    }
  }
  var self;
  /** @type {string} */
  var udataCur = "https://propertysearch.dnrm.qld.gov.au/startTransaction";
  return{
    /** @type {function (): undefined} */
    setup : setup,
    /** @type {function (?): undefined} */
    viewModel : setUser,
    template : {
      element : "cart"
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.lotplansearchfields = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
    $(document).on("loadingFinished", false, function(dataAndEvents) {
      self.loading(false);
    });
  }
  /**
   * @param {?} failing_message
   * @return {undefined}
   */
  function report(failing_message) {
    self = this;
    self.loading = ko.observable(false);
    self.isError = ko.observable(false);
    /** @type {function (?, ?): undefined} */
    self.makeLotPlanSearch = render;
    /** @type {function (?, Event): undefined} */
    self.resetLotPlanSearch = handler;
    self.searchLot = ko.observable("");
    self.searchPlan = ko.observable("");
  }
  /**
   * @param {?} context
   * @param {?} rows
   * @return {undefined}
   */
  function render(context, rows) {
    self.loading(true);
    this.isError(false);
    lotplan.main.clearData();
    self.searchLot($(context).find("#lot_plan_search_lotno").val().trim());
    self.searchPlan($(context).find("#lot_plan_search_planno").val().toUpperCase().trim());
    if (self.searchLot() && self.searchPlan()) {
      load();
    } else {
      self.loading(false);
      this.isError(true);
    }
  }
  /**
   * @param {?} token
   * @param {Event} ev
   * @return {undefined}
   */
  function handler(token, ev) {
    $(ev.target).closest("form").find('input[type="text"]').val("");
    this.isError(false);
    lotplan.main.clearData();
  }
  /**
   * @return {undefined}
   */
  function load() {
    if (self.searchPlan().match("BUP") > 0 || self.searchPlan().match("SP") > 0) {
      lotplan.utils.ajaxRequest(p + "BUP_LOTPLAN+%3D+%27" + self.searchLot() + self.searchPlan() + "%27", "GET", null, function(data) {
        if (data && JSON.parse(data).features.length > 0) {
          lotplan.utils.ajaxRequest(OBJECTID + JSON.parse(data).features[0].attributes.OBJECTID, "GET", null, lotplan.components.searchresults.setSearchData.bind(self));
        } else {
          lotplan.components.searchresults.setSearchData.bind(null);
          self.isError(true);
          self.loading(false);
        }
      });
    } else {
      lotplan.utils.ajaxRequest(c + "LOTPLAN+%3D+%27" + self.searchLot() + self.searchPlan() + "%27", "GET", null, function(data) {
        if (JSON.parse(data).features.length > 0) {
          /** @type {string} */
          var num = "";
          var name;
          for (name in JSON.parse(data).features) {
            if (name > 0) {
              num += "+OR+";
            }
            num += "LOTPLAN+%3D+%27" + JSON.parse(data).features[name].attributes.LOTPLAN + "%27";
          }
          lotplan.utils.ajaxRequest(memo + num, "GET", null, lotplan.components.searchresults.setSearchData.bind(self));
        } else {
          lotplan.components.searchresults.setSearchData.bind(null);
          self.isError(true);
          self.loading(false);
        }
      });
    }
  }
  var self;
  /** @type {string} */
  var memo = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/0/query?text=&geometryType=esriGeometryPoint&spatialRel=esriSpatialRelIntersects&returnIdsOnly=false&returnGeometry=true&outFields=*&f=json&returnCountOnly=false&where=";
  /** @type {string} */
  var c = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/4/query?text=&geometryType=esriGeometryPoint&spatialRel=esriSpatialRelIntersects&returnIdsOnly=false&returnGeometry=true&outFields=*&f=json&returnCountOnly=false&where=";
  /** @type {string} */
  var p = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/21/query?text=&geometryType=esriGeometryPoint&spatialRel=esriSpatialRelIntersects&returnIdsOnly=false&returnGeometry=true&outFields=*&f=json&returnCountOnly=false&where=";
  /** @type {string} */
  var OBJECTID = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/21/queryRelatedRecords?relationshipId=1&outFields=*&definitionExpression=&returnGeometry=true&maxAllowableOffset=&geometryPrecision=&outSR=&returnZ=false&returnM=false&gdbVersion=&f=json&objectIds=";
  return{
    /** @type {function (): undefined} */
    setup : setup,
    /**
     * @param {?} err
     * @return {undefined}
     */
    setError : function(err) {
      self.isError(err);
    },
    /** @type {function (?): undefined} */
    viewModel : report,
    template : {
      element : "lotPlanSearchFields"
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.receipt = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
    urlParams = lotplan.utils.getUrlParamsObject();
    data.number(decodeURIComponent(urlParams.number));
    data.date(decodeURIComponent(urlParams.date).replace(/\+/g, " "));
    data.total(decodeURIComponent(urlParams.total));
    data.email(decodeURIComponent(urlParams.email));
    data.errorMessage(decodeURIComponent(urlParams.errorMessage).replace(/\+/g, " "));
    setTimeout(function() {
      lotplan.main.emptyCart();
    }, 200);
  }
  /**
   * @param {?} contactInitDict
   * @return {undefined}
   */
  function Contact_ContactInit(contactInitDict) {
    data = this;
    /** @type {function (?, ?): undefined} */
    data.newSearch = poll;
    data.number = ko.observable();
    data.date = ko.observable();
    data.total = ko.observable();
    data.email = ko.observable();
    data.errorMessage = ko.observable();
    data.pats_server = ko.observable(pats_server);
    data.products = ko.observable();
  }
  /**
   * @param {?} delayInitialWork
   * @param {?} verifier
   * @return {undefined}
   */
  function poll(delayInitialWork, verifier) {
    /** @type {string} */
    window.location.hash = "page=search";
  }
  var data;
  return pats_server = "https://propertysearch.dnrm.qld.gov.au", {
    /** @type {function (): undefined} */
    setup : setup,
    /** @type {function (?): undefined} */
    viewModel : Contact_ContactInit,
    template : {
      element : "receipt"
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.review = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
  }
  /**
   * @param {?} environment
   * @return {undefined}
   */
  function main(environment) {
    $scope = this;
    $scope.added = ko.observable(false);
    /** @type {function (?, ?): undefined} */
    $scope.backToSearch = map;
    /** @type {function (?, ?): undefined} */
    $scope.newSearch = navigate;
    /** @type {function (?, ?): undefined} */
    $scope.addToCart = open;
    $scope.lotplanGroupKeys = ko.pureComputed(function() {
      return Object.keys($scope.lotplanGroups());
    });
    $scope.lotplanGroups = ko.computed(function() {
      var json = {};
      var $ = lotplan.main.getSelectedData();
      /** @type {number} */
      var type = 0;
      for (;type < $().length;type++) {
        var options = $()[type];
        /** @type {string} */
        var address = "_" + options.LOT + options.PLAN;
        if (!json[address]) {
          json[address] = {
            lotplan : options.LOT + "/" + options.PLAN,
            titles : [],
            products : $()[type].PRODUCTS(),
            address : options.ADDRESS
          };
        }
        json[address].titles.push($()[type].LOT_TITLES());
      }
      return json;
    });
    /**
     * @param {?} i
     * @param {string} product
     * @return {?}
     */
    $scope.selectedProductAlreadyInCartTooltip = function(i, product) {
      return lotplan.main.cartHelper().find({
        product : product,
        data : $scope.lotplanGroups()[i]
      }, true) ? "Added to cart" : "Add to cart";
    };
    /**
     * @param {?} i
     * @param {string} product
     * @return {?}
     */
    $scope.selectedProductAlreadyInCart = function(i, product) {
      return!!lotplan.main.cartHelper().find({
        product : product,
        data : $scope.lotplanGroups()[i]
      }, true);
    };
    /**
     * @param {?} i
     * @param {string} item
     * @param {Event} option
     * @return {?}
     */
    $scope.checkSelectedProduct = function(i, item, option) {
      if (option.target.disabled) {
        lotplan.main.getSelectedProducts().remove(function(d) {
          return d.data === $scope.lotplanGroups()[i] && d.product === item;
        });
      } else {
        if (lotplan.main.cartHelper().find({
          product : item,
          data : $scope.lotplanGroups()[i]
        }, true)) {
          return true;
        }
        lotplan.main.getSelectedProducts().push({
          data : $scope.lotplanGroups()[i],
          product : item
        });
        $scope.addToCart();
      }
      return false;
    };
    $scope.selectedProductTotal = ko.pureComputed(function() {
      /** @type {number} */
      var sum = 0;
      /** @type {number} */
      var i = 0;
      for (;i < lotplan.main.getSelectedProducts()().length;i++) {
        sum += parseFloat(lotplan.main.getSelectedProducts()()[i].product.price);
      }
      return sum;
    });
    $scope.alertType = ko.observable("");
    $scope.alertMessage = ko.observable("");
    /**
     * @return {undefined}
     */
    $scope.alertClose = function() {
      $scope.alertType("");
      $scope.alertMessage("");
    };
    $scope.productsLoading = lotplan.main.productsLoading();
    var codeSegments = lotplan.main.getSelectedData()();
    /** @type {number} */
    var i = 0;
    for (;i < codeSegments.length;i++) {
      lotplan.main.setLotProduct(codeSegments[i]);
    }
  }
  /**
   * @param {?} min2
   * @param {?} reject
   * @return {undefined}
   */
  function map(min2, reject) {
    lotplan.main.clearData(false, true, true);
    /** @type {string} */
    window.location.hash = "page=search";
  }
  /**
   * @param {?} triggerRoute
   * @param {?} step
   * @return {undefined}
   */
  function navigate(triggerRoute, step) {
    lotplan.main.clearData(true, true, true);
    /** @type {string} */
    window.location.hash = "page=search";
  }
  /**
   * @param {?} opt_async
   * @param {?} opt_password
   * @return {undefined}
   */
  function open(opt_async, opt_password) {
    var _createTabGroup = lotplan.main.getSelectedProducts();
    if (lotplan.main.cartHelper().add(_createTabGroup(), true, function() {
      $scope.alertType("danger");
      $scope.alertMessage("<p>You have reached the limit of your cart, no more products can be added.</p><p>Please proceed to the checkout and either purchase or remove items in your cart to continue ordering.</p>");
    })) {
      $scope.added(true);
      $(".productcheckbox").each(function(dataAndEvents, elem) {
        if ($(elem).is(":checked")) {
          $(elem).removeAttr("checked").hide().before('<span class="glyphicon glyphicon-ok" data-toggle="tooltip" data-placement="top" title="This product has been added to your cart."></span>');
          $(".glyphicon-tick").tooltip();
        }
      });
      lotplan.main.clearData(false, false, true);
    }
  }
  var $scope;
  return{
    /** @type {function (): undefined} */
    setup : setup,
    /** @type {function (?): undefined} */
    viewModel : main,
    template : {
      element : "review"
    },
    /**
     * @param {string} type
     * @param {string} status
     * @return {undefined}
     */
    setAlert : function(type, status) {
      $scope.alertType(type);
      $scope.alertMessage(status);
    },
    synchronous : true
  };
}(jQuery, ko), "object" != typeof lotplan && (lotplan = {}), "object" != typeof lotplan.components && (lotplan.components = {}), lotplan.components.searchresults = function($, ko) {
  /**
   * @return {undefined}
   */
  function setup() {
  }
  /**
   * @return {undefined}
   */
  function init() {
	  debugger
    var map = L.map("map").setView([-33.86617516416043, 151.2077522277832], 15);
    if (L.Icon.Default.imagePath = "https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/", L.tileLayer("https://api{s}.nowwhere.com.au/1.1.2/tile/50/{z}/{x}/{y}/?key=oDYTVpCsmigGKoxiy7ZxyNpIasMjEcMelJqTyz1x", {
      minZoom : 5,
      maxZoom : 18,
      attribution : "Map data &copy; MapData Services",
      subdomains : ["1", "2", "3", "4"]
    }).addTo(map), codeSegments) {
      /** @type {number} */
      i = 0;
      for (;i < codeSegments.length;i++) {
        map.removeLayer(codeSegments[i]);
      }
    }
    var mapBounds = L.latLngBounds([]);
    /** @type {Array} */
    var codeSegments = [];
    var $ = lotplan.main.getSearchData();
    var imgpath = new L.Icon.Default({
      iconUrl : "https://www.dnrm.qld.gov.au/?a=332335"
    });
    var setIcon = new L.Icon.Default;
    /** @type {number} */
    var i = 0;
    for (;i < $().length;i++) {
      var marker;
      var node = $()[i];
      var attrs = node.attributes;
      if (node.geometry.rings) {
        var b;
        var a;
        var g;
        var f;
        var unlock;
        for (unlock in node.geometry.rings[0]) {
          if (0 == unlock) {
            b = node.geometry.rings[0][unlock][0];
            a = node.geometry.rings[0][unlock][0];
            g = node.geometry.rings[0][unlock][1];
            f = node.geometry.rings[0][unlock][1];
          }
          if (unlock > 0) {
            if (node.geometry.rings[0][unlock][0] < b) {
              b = node.geometry.rings[0][unlock][0];
            }
          }
          if (unlock > 0) {
            if (node.geometry.rings[0][unlock][0] > a) {
              a = node.geometry.rings[0][unlock][0];
            }
          }
          if (unlock > 0) {
            if (node.geometry.rings[0][unlock][1] < g) {
              g = node.geometry.rings[0][unlock][1];
            }
          }
          if (unlock > 0) {
            if (node.geometry.rings[0][unlock][1] > f) {
              f = node.geometry.rings[0][unlock][1];
            }
          }
        }
        marker = L.marker([(g - b) / 2, (a - b) / 2], {
          title : "Lot: " + attrs.LOT + " plan: " + attrs.PLAN,
          opacity : 1,
          icon : imgpath
        });
      } else {
        marker = L.marker([node.geometry.y, node.geometry.x], {
          title : "Lot: " + attrs.LOT + " plan: " + attrs.PLAN,
          opacity : 1,
          icon : imgpath
        });
      }
      marker.on("click", function(opt_e) {
        var classes = lotplan.main.getSelectedData();
        if (~classes.indexOf(opt_e.target.lotdata)) {
          classes.remove(opt_e.target.lotdata);
        } else {
          classes.push(opt_e.target.lotdata);
        }
      });
      marker.lotdata = attrs;
      codeSegments.push(marker.addTo(map));
      mapBounds.extend(marker.getLatLng());
    }
    map.fitBounds(mapBounds);
    L.esri.featureLayer({
      url : appFrontendUrl,
      style : {
        opacity : 0,
        weight : 1
      },
      /**
       * @param {Object} feature
       * @param {Node} layer
       * @return {undefined}
       */
      onEachFeature : function(feature, layer) {
        layer.setStyle({
          fill : false,
          color : "#000"
        });
        /** @type {number} */
        var j = 0;
        for (;j < $().length;j++) {
          if (feature.properties.LOT == $()[j].attributes.LOT) {
            if (feature.properties.PLAN == $()[j].attributes.PLAN) {
              layer.setStyle({
                fill : true,
                color : "red"
              });
            }
          }
        }
      }
    }).addTo(map);
    lotplan.main.getSelectedData().subscribe(function(whitespace) {
      if (whitespace.length > 0) {
        lotplan.main.setLotProduct(whitespace[whitespace.length - 1]);
      }
      /** @type {number} */
      var i = 0;
      for (;i < codeSegments.length;i++) {
        codeSegments[i].setIcon(~whitespace.indexOf(codeSegments[i].lotdata) ? setIcon : imgpath);
      }
    });
    initialize();
  }
  /**
   * @return {undefined}
   */
  function initialize() {
    $("#results tbody td").on("click", function(ev) {
      if (!$(ev.target).is("input")) {
        $(ev.target).parent().find("input").trigger("click");
      }
    });
  }
  /**
   * @param {?} initialState
   * @return {undefined}
   */
  function Game(initialState) {
    waitingOnProducts = this;
    waitingOnProducts.waitingOnProducts = ko.observable(false);
    if (lotplan.main) {
      if (lotplan.main.getSearchData()) {
        if (lotplan.main.getSearchData()().length > 0) {
          init();
        }
      }
    }
  }
  /**
   * @param {?} data
   * @return {undefined}
   */
  function build(data) {
    if (data) {
      /** @type {*} */
      var section = JSON.parse(data);
      if (section.relatedRecordGroups && (section.relatedRecordGroups.length > 0 && (section.features = section.relatedRecordGroups[0].relatedRecords)), section.features && section.features.length > 0) {
        /** @type {number} */
        var i = 0;
        for (;i < section.features.length;i++) {
          var attrs = section.features[i].attributes;
          attrs.LOT_TITLES = ko.observableArray();
          attrs.LOT_VALID = ko.observable(false);
          attrs.PRODUCTS = ko.observableArray();
        }
        lotplan.main.getSearchData()(section.features);
        init();
      } else {
        this.isError(true);
        lotplan.main.getSearchData()(false);
      }
    } else {
      lotplan.main.getSearchData()(false);
    }
    $(document).trigger("loadingFinished");
  }
  var waitingOnProducts;
  /** @type {string} */
  var appFrontendUrl = "https://gisservices.information.qld.gov.au/arcgis/rest/services/PlanningCadastre/LandParcelPropertyFramework/MapServer/4";
  return{
    /** @type {function (): undefined} */
    setup : setup,
    /** @type {function (): undefined} */
    setupMap : init,
    /** @type {function (?): undefined} */
    setSearchData : build,
    /** @type {function (?): undefined} */
    viewModel : Game,
    template : {
      element : "searchResults"
    },
    synchronous : true
  };
}(jQuery, ko), void 0 === lotplan) {
  var lotplan = {}
}
lotplan.utils = function($) {
  /**
   * @param {string} value
   * @param {string} method
   * @param {Object} lab
   * @param {Function} callback
   * @param {Function} iterator
   * @return {undefined}
   */
  function next(value, method, lab, callback, iterator) {
    var test = $.ajax({
      url : value,
      method : method,
      crossDomain : true,
      data : lab,
      timeout : 15E3,
      xhrFields : {
        withCredentials : false
      }
    });
    test.done(function(basis, dataAndEvents, deepDataAndEvents) {
      callback(basis);
    });
    test.fail(function(context, dataAndEvents, deepDataAndEvents) {
      return iterator ? iterator.call(context) : void(401 == context.status && handleUnauthorised());
    });
  }
  /**
   * @param {string} elem
   * @return {?}
   */
  function get(elem) {
    var deferred = $.Deferred();
    return next(elem, "GET", null, function(translation) {
      deferred.resolve(translation);
    }), deferred.promise();
  }
  /**
   * @param {number} number
   * @return {?}
   */
  function format(number) {
    return number = number || 0, "$" + parseFloat(number).toFixed(2);
  }
  /**
   * @return {undefined}
   */
  function throttledUpdate() {
    /**
     * @param {?} id
     * @return {?}
     */
    $.Topic = function(id) {
      var callbacks;
      var topic = id && topics[id];
      return topic || (callbacks = $.Callbacks(), topic = {
        publish : callbacks.fire,
        subscribe : callbacks.add,
        unsubscribe : callbacks.remove
      }, id && (topics[id] = topic)), topic;
    };
  }
  /**
   * @return {undefined}
   */
  function Model() {
    /** @type {Array} */
    this.items = [];
    /** @type {Array} */
    this.errors = [];
  }
  /**
   * @param {Array} value
   * @param {string} separator
   * @return {undefined}
   */
  function self(value, separator) {
    /** @type {number} */
    this.maxLength = 3800;
    /** @type {number} */
    this.cookieTimeout = 60;
    this.cookieName = separator || "";
    /** @type {Array} */
    this.items = value;
  }
  /**
   * @param {Object} element
   * @param {Function} compiled
   * @return {undefined}
   */
  function postLink(element, compiled) {
    var result;
    var tref;
    /** @type {number} */
    var backoff = 200;
    /**
     * @param {Object} handler
     * @return {undefined}
     */
    var reset = function(handler) {
      if (handler) {
        if (handler.abort) {
          handler.abort();
        }
      }
    };
    element.typeahead({
      minLength : 3,
      highlight : false,
      limit : 20
    }, {
      /**
       * @param {?} request
       * @param {?} options
       * @param {?} capture
       * @return {undefined}
       */
      source : function(request, options, capture) {
        if (tref) {
          clearTimeout(tref);
        }
        reset(result);
        /** @type {number} */
        tref = setTimeout(function() {
          reset(result);
          result = compiled.call(this, request, options, capture);
        }, backoff);
      }
    });
    element.bind("typeahead:select", function(dataAndEvents, value) {
      element.val(value);
      element.typeahead("val", value);
    });
  }
  /**
   * @return {?}
   */
  function set() {
    var result = {};
    /** @type {Array.<string>} */
    var values = window.location.hash.substr(1).split("&");
    /** @type {number} */
    var i = 0;
    /** @type {number} */
    var valuesLen = values.length;
    for (;i < valuesLen;i++) {
      /** @type {Array.<string>} */
      var both = values[i].split("=");
      /** @type {string} */
      result[both[0]] = both[1];
    }
    return result;
  }
  /**
   * @param {string} key
   * @param {string} value
   * @param {number} opt_attributes
   * @return {undefined}
   */
  function setCookie(key, value, opt_attributes) {
    /** @type {Date} */
    var expires = new Date;
    expires.setTime(expires.getTime() + 60 * opt_attributes * 60 * 1E3);
    /** @type {string} */
    var c_value = "expires=" + expires.toUTCString();
    /** @type {string} */
    document.cookie = key + "=" + value + "; " + c_value;
  }
  /**
   * @param {string} key
   * @return {?}
   */
  function getCookie(key) {
    /** @type {string} */
    var keyString = key + "=";
    /** @type {Array.<string>} */
    var codeSegments = document.cookie.split(";");
    /** @type {number} */
    var i = 0;
    for (;i < codeSegments.length;i++) {
      /** @type {string} */
      var cookie = codeSegments[i];
      for (;" " == cookie.charAt(0);) {
        /** @type {string} */
        cookie = cookie.substring(1);
      }
      if (0 == cookie.indexOf(keyString)) {
        return cookie.substring(keyString.length, cookie.length);
      }
    }
    return "";
  }
  return Model.prototype.add = function(msg, fn) {
    return this.items.push({
      msg : msg,
      /** @type {Function} */
      fn : fn
    }), this;
  }, Model.prototype.validate = function(self, expected) {
    /** @type {number} */
    var i = 0;
    for (;i < this.items.length;i++) {
      if (!this.items[i].fn.call(self || this)) {
        this.errors.push(this.items[i].msg);
      }
    }
    return expected ? expected.call(this, this.valid(), this.errors.length) : this.valid();
  }, Model.prototype.errorMessage = function(far) {
    /** @type {string} */
    var near = "<ul>";
    /** @type {number} */
    var i = 0;
    for (;i < this.errors.length;i++) {
      near += "<li>" + this.errors[i] + "</li>";
    }
    return far + near + "</ul>";
  }, Model.prototype.valid = function() {
    return!this.errors.length;
  }, self.prototype.remove = function(key) {
    /** @type {boolean} */
    var t = false;
    var child = this.find(key);
    var found = child ? this.items.indexOf(key) : -1;
    return~found && (t = !!this.items.splice(found, 1).length), this.updateCookie(), t;
  }, self.prototype.find = function(b, signal_eof) {
    b = signal_eof ? this.converter.call(this, b) : b;
    /** @type {null} */
    var waitMsg = null;
    return this.forEach(this.items(), function(a, capture) {
      if (a === b || this.equality.call(this, a, b, capture)) {
        return!(waitMsg = a);
      }
    }), waitMsg;
  }, self.prototype.add = function(a, b, block) {
    return a = b ? this.convert(a) : a, block && this.willExceedLimit(a, false) ? (block.call(this), false) : (this.items.push.apply(this.items, a), this.updateCookie(), true);
  }, self.prototype.willExceedLimit = function(v02, recurring) {
    var right = this.serialize().length;
    var left = this.serialize(recurring ? this.convert(v02) : v02).length;
    return right + left > this.maxLength;
  }, self.prototype.getTotal = function() {
    /** @type {number} */
    var sum = 0;
    return this.forEach(this.items(), function(mapper, force) {
      sum += parseFloat(this.getAmount.call(this, mapper, force));
    }), sum;
  }, self.prototype.forEach = function(arr, fn) {
    /** @type {number} */
    var i = 0;
    for (;i < arr.length && fn.call(this, arr[i], i) !== false;i++) {
    }
  }, self.prototype.serialize = function(e) {
    return e = e || (this.items() || []), JSON.stringify(e);
  }, self.prototype.updateCookie = function(dataAndEvents) {
    setCookie(this.cookieName, dataAndEvents ? "" : this.serialize(), this.cookieTimeout);
  }, self.prototype.convert = function(v02) {
    /** @type {Array} */
    var collect = [];
    return this.forEach(v02, function(value, index) {
      collect.push(this.converter.call(this, value, index));
    }), collect;
  }, self.prototype.initalise = function(dataAndEvents) {
    try {
      this.items.push.apply(this.items, JSON.parse(lotplan.utils.getCookie(this.cookieName)));
    } catch (t) {
      this.updateCookie(true);
    }
  }, self.prototype.converter = function($scope, str) {
    return{
      i : $scope.product.pid,
      a : $scope.product.price,
      t : $scope.product.title,
      l : $scope.product.lot,
      pt : $scope.product.plan_type,
      pn : $scope.product.plan_num
    };
  }, self.prototype.equality = function(item, self, b) {
    return 1 == item.i ? item.i == self.i && item.t == self.t : !!(item.i = 2) && (item.i == self.i && (item.pt == self.pt && item.pn == self.pn));
  }, self.prototype.getAmount = function(fnc, dataAndEvents) {
    return fnc.a;
  }, {
    setupPubSub : throttledUpdate(),
    /** @type {function (string): ?} */
    multiAjaxRequest : get,
    /** @type {function (): ?} */
    getUrlParamsObject : set,
    /** @type {function (string, string, Object, Function, Function): undefined} */
    ajaxRequest : next,
    /** @type {function (number): ?} */
    formatCurrency : format,
    /** @type {function (string, string, number): undefined} */
    setCookie : setCookie,
    /** @type {function (string): ?} */
    getCookie : getCookie,
    /** @type {function (Object, Function): undefined} */
    TypeaheadWrapper : postLink,
    /** @type {function (): undefined} */
    Validator : Model,
    /** @type {function (Array, string): undefined} */
    Cart : self
  };
}(jQuery), "object" != typeof lotplan && (lotplan = {}), lotplan.main = function($, ko) {
  /**
   * @return {undefined}
   */
  function constructor() {
    self.isLoading(true);
    ko.components.register("lotplansearchfields", lotplan.components.lotplansearchfields);
    ko.components.register("addresssearchfields", lotplan.components.addresssearchfields);
    ko.components.register("searchresults", lotplan.components.searchresults);
    ko.components.register("review", lotplan.components.review);
    ko.components.register("cart", lotplan.components.cart);
    ko.components.register("receipt", lotplan.components.receipt);
    ko.applyBindings(self);
    initialize();
  }
  /**
   * @return {undefined}
   */
  function initialize() {
    $(window).on("hashchange", function() {
      init();
    });
    self.cartHelper.initalise(self.cartItems);
    $(document).ready(function() {
      $("body").tooltip({
        selector : "[data-toggle=tooltip]"
      });
    });
    init();
  }
  /**
   * @return {undefined}
   */
  function updateViewModel() {
    self = this;
    self.isLoading = ko.observable();
    self.pageRoute = ko.observable("");
    self.selectedData = ko.observableArray();
    self.searchData = ko.observableArray();
    self.selectedProducts = ko.observableArray();
    self.productsLoading = ko.observable(false);
    self.errorText = ko.observable();
    /** @type {function (number): ?} */
    self.formatCurrency = parse;
    /** @type {function (?, string, string): ?} */
    self.getLotSummary = open;
    self.cartItems = ko.observableArray();
    self.cartHelper = new lotplan.utils.Cart(self.cartItems, "cart");
    /** @type {function (?, ?): undefined} */
    self.viewDetails = callback;
    /** @type {function (?, Event): undefined} */
    self.goToCart = reset;
    /** @type {function (?, Event): ?} */
    self.checkAllSearchData = listener;
  }
  /**
   * @return {undefined}
   */
  function init() {
    var c = lotplan.utils.getUrlParamsObject();
    if ("review" === c.page) {
      if (self.selectedData().length > 0) {
        self.pageRoute("review");
        lotplan.components.review.setup();
      } else {
        /** @type {string} */
        window.location.hash = "page=search";
        self.pageRoute("search");
        remove(false, true, true);
        lotplan.components.addresssearchfields.setup();
        lotplan.components.lotplansearchfields.setup();
      }
    } else {
      if ("review" === c.page) {
        self.pageRoute("review");
        lotplan.components.review.setup();
      } else {
        if ("cart" === c.page) {
          remove(true, false, true);
          self.pageRoute("cart");
          lotplan.components.cart.setup();
        } else {
          if ("receipt" === c.page) {
            self.pageRoute("receipt");
            lotplan.components.receipt.setup();
          } else {
            if (!(void 0 !== c.page && "search" !== c.page)) {
              /** @type {string} */
              window.location.hash = "page=search";
              self.pageRoute("search");
              remove(false, true, true);
              lotplan.components.addresssearchfields.setup();
              lotplan.components.lotplansearchfields.setup();
            }
          }
        }
      }
    }
    self.isLoading(false);
  }
  /**
   * @param {?} data
   * @param {Event} e
   * @return {?}
   */
  function listener(data, e) {
    if (self.selectedData.removeAll(), e.target.checked) {
      /** @type {number} */
      var pi = 0;
      for (;pi < self.searchData().length;pi++) {
        self.selectedData.push(self.searchData()[pi].attributes);
      }
    }
    return true;
  }
  /**
   * @param {?} o
   * @return {undefined}
   */
  function compile(o) {
    if (!(o.LOT_TITLES() && o.LOT_TITLES().count > 0)) {
      var parts = o.PLAN.match(/(\d+|\D+)/g);
      if (parts) {
        if (parts.length > 0) {
          f++;
          self.productsLoading(true);
          if (!("CP" == parts[0])) {
            if (!("LB" == parts[0])) {
              if (!("RP" == parts[0])) {
                if (!("SP" == parts[0])) {
                  if (!("UB" == parts[0])) {
                    if (!("BPA" == parts[0])) {
                      if (!("BRP" == parts[0])) {
                        if (!("BUP" == parts[0])) {
                          if (!("GRP" == parts[0])) {
                            if (!("GTA" == parts[0])) {
                              if (!("GTP" == parts[0])) {
                                if (!("MCP" == parts[0])) {
                                  if (!("MPP" == parts[0])) {
                                    if (!("MSP" == parts[0])) {
                                      if (!("SBA" == parts[0])) {
                                        if (!("SBP" == parts[0])) {
                                          if (!("SPA" == parts[0])) {
                                            if (!("SPS" == parts[0])) {
                                              if (!("SRP" == parts[0])) {
                                                if (!("SSP" == parts[0])) {
                                                  parts[1] = parts[0] + parts[1];
                                                  /** @type {string} */
                                                  parts[0] = "CP";
                                                }
                                              }
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          lotplan.utils.ajaxRequest(udataCur, "POST", {
            lotnum : o.LOT,
            plannum : parts[1],
            plantype : parts[0]
          }, function(data) {
            return!data || !data.titlerefs_count > 0 ? ("Service returned error." == data && lotplan.components.review.setAlert("danger", "There has been an unexpected error with our service. Please email <a href='mailto:propertysearch@dnrm.qld.gov.au?subject=Titles%20search%20service%20error'>web@dnrm.qld.gov.au</a> with the details of the property you are searching for."), "Service connection fault." == data && lotplan.components.review.setAlert("danger", "Sorry, our service has experienced a connection error. Please return to the search page and try again later. If you continue to have problems, email <a href='mailto:propertysearch@dnrm.qld.gov.au?subject=Titles%20search%20service%20downtime'>propertysearch@dnrm.qld.gov.au</a>."), 
            "No title records found matching selection." == data && lotplan.components.review.setAlert("info", "We can't find any products for this lot on plan. In some cases, such as with water titles or community title schemes, a product may be available from our <a style='font-style:underline;' href='https://www.dnrm.qld.gov.au/our-department/contact-us/dnrm-business-centre-contacts' target='_blank'>business centres</a>."), self.productsLoading(false), false) : (o.LOT_VALID(true), $.each(data.titlerefs, 
            function(dataAndEvents, item) {
              if (o.LOT_TITLES().indexOf(item.title_reference) == -1) {
                o.LOT_TITLES().push(item.title_reference);
                o.PRODUCTS().push({
                  lot : o.LOT,
                  plan_num : parts[1],
                  plan_type : parts[0],
                  pid : item.pid,
                  title : item.title_reference,
                  name : "Current title search " + item.title_reference,
                  price : item.price,
                  sample : "https://dnrm.qld.gov.au/__data/assets/pdf_file/0008/366128/title-search-sample.pdf"
                });
              }
            }), "Y" == data.imaged_flag && (JSON.stringify(o.PRODUCTS()).search(JSON.stringify({
              lot : o.LOT,
              plan_num : parts[1],
              plan_type : parts[0],
              pid : data.image_pid,
              name : "Survey plan " + o.LOT + "/" + parts[0] + parts[1],
              price : data.image_price,
              sample : "https://dnrm.qld.gov.au/__data/assets/file/0010/367840/survey-plan-sample.tiff"
            })) == -1 && o.PRODUCTS().push({
              lot : o.LOT,
              plan_num : parts[1],
              plan_type : parts[0],
              pid : data.image_pid,
              name : "Survey plan " + o.LOT + "/" + parts[0] + parts[1],
              price : data.image_price,
              sample : "https://dnrm.qld.gov.au/__data/assets/file/0010/367840/survey-plan-sample.tiff"
            })), f--, void(0 == f && self.productsLoading(false)));
          }, function(dataAndEvents) {
            lotplan.componentnts.review.setAlert("danger", "There has been an unexpected error with our service. Please email <a href='mailto:web@dnrm.qld.gov.au?subject=Titles%20search%20service%20error'>web@dnrm.qld.gov.au</a> with the details of the property you are searching for.");
            self.productsLoading(false);
          });
        }
      }
    }
  }
  /**
   * @param {boolean} recurring
   * @param {boolean} v33
   * @param {boolean} keepData
   * @return {undefined}
   */
  function remove(recurring, v33, keepData) {
    if (recurring || void 0 === recurring) {
      if (self.searchData().length) {
        self.searchData.removeAll();
      }
    }
    if (v33 || void 0 === v33) {
      if (self.selectedData().length) {
        self.selectedData.removeAll();
      }
    }
    if (keepData || void 0 === keepData) {
      if (self.selectedProducts().length) {
        self.selectedProducts.removeAll();
      }
    }
  }
  /**
   * @param {number} execResult
   * @return {?}
   */
  function parse(execResult) {
    return "$" + parseFloat(execResult || 0).toFixed(2);
  }
  /**
   * @param {?} x
   * @param {string} y
   * @param {string} charset
   * @return {?}
   */
  function open(x, y, charset) {
    return "Lot on plan: " + (x + "/" + y) + (charset ? " - Title: " + charset : "");
  }
  /**
   * @param {?} models
   * @param {Event} evt
   * @return {undefined}
   */
  function reset(models, evt) {
    $(evt.target).attr("disabled", "disabled");
    /** @type {string} */
    window.location.hash = "page=cart";
    remove(true, false, false);
  }
  /**
   * @param {?} __
   * @param {?} success
   * @return {undefined}
   */
  function callback(__, success) {
    window.scrollTo(0, 0);
    self.selectedProducts.removeAll();
    /** @type {string} */
    window.location.hash = "page=review";
  }
  var self = new updateViewModel;
  /** @type {string} */
  var udataCur = "https://propertysearch.dnrm.qld.gov.au/productsAvailable";
  /** @type {number} */
  var f = 0;
  return{
    constructor : constructor(),
    /** @type {function (?, Event): undefined} */
    goToCart : reset,
    /** @type {function (?): undefined} */
    setLotProduct : compile,
    /** @type {function (boolean, boolean, boolean): undefined} */
    clearData : remove,
    /**
     * @return {?}
     */
    productsLoading : function() {
      return self.productsLoading;
    },
    /**
     * @return {?}
     */
    cartHelper : function() {
      return self.cartHelper;
    },
    /**
     * @return {?}
     */
    emptyCart : function() {
      return self.cartItems.removeAll(), self.cartHelper.updateCookie(true), true;
    },
    /**
     * @return {?}
     */
    getSearchData : function() {
      return self.searchData;
    },
    /**
     * @return {?}
     */
    getSelectedData : function() {
      return self.selectedData;
    },
    /**
     * @return {?}
     */
    getSelectedProducts : function() {
      return self.selectedProducts;
    }
  };
}(jQuery, ko);
