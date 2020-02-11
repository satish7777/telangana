'use strict';

app.controller('liveReportController', ['$scope','$http',  function($scope, $http) {
	
	var vm = $scope;
	vm.liverpt={};
	 vm.displayUserSrchValMsg = true;
	 vm.noData=false;
     vm.sort = function(keyname){
         $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa
     }
 	
     vm.validatePhoneNumber = function(number) {
         var phoneno = /^\d{10}$/;
         // if(number.value().match(phoneno)) {
         if (phoneno.test(number)) {
             return true;
         } else {
             return false;
         }
     };
     vm.dataLoaded=false;
	vm.searchLiveReport = function(){
		  vm.dataLoaded=false;
		  vm.mobileDetails=[];
        if ((vm.liverpt.mobile == undefined || vm.liverpt.mobile == "") ) {
            vm.displayUserSrchValMsg = false;
            vm.displayMsg="Please enter Mobile Number";
        }else if(!vm.validatePhoneNumber(vm.liverpt.mobile)){
        	vm.displayUserSrchValMsg = false;
            vm.displayMsg="Please enter Valid 10 digit Mobile Number";
        }else {
            vm.displayUserSrchValMsg = true;
            var parameter = JSON.stringify({
          	 mobile: vm.liverpt.mobile
         });
            vm.dataLoaded=true;
            vm.noData=false;
            $http.post('/api/liverrpt/searchMsisdnDetails',parameter).then(function (response){
                    var data = response.data.data;
                        vm.mobileDetails = data;
                        vm.dataLoaded=false;
                        if(vm.mobileDetails.length==0){
                        	vm.noData=true;
                        }
            });
            
        }
           
	}
	vm.exportLiveAction= function(){
		if(vm.mobileDetails==undefined || vm.mobileDetails.length==0){
			
		}else{
			 vm.fileAccName = 'LiveReport';
			 alasql("SELECT msgId as MessageID,mobile as MobileNo,message as Message,sender as Sender,submitTime as SubmitTime,deliverTime as DeliverTime,deliverStatus as DeliveryStatus,errorCode as ErrorCode INTO XLSX(" + "'" + vm.fileAccName + ".xlsx" + "'" + ", {headers:true}) FROM ?", [vm.mobileDetails]);
			 
		}
		
		 
	}
	
	
	
}]);