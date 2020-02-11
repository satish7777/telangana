'use strict';

app.controller('cdrReportController', ['$scope','$http',  function($scope, $http) {
	
	var vm = $scope;
	vm.cdrrpt={};
	vm.noData=false;
	 vm.displayUserSrchValMsg = true;
     var maxDate = new Date();
     vm.minDate = new Date(maxDate.getFullYear(), maxDate.getMonth()-3, maxDate.getDate()).toDateString();
     vm.maxLimit=new Date().toDateString();
     vm.disableEndDate=true;
     vm.sort = function(keyname){
         $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa
     }
 	
	vm.enableEndDate = function(){
		vm.cdrrpt.endDate=null;
		vm.maxEndDateS = vm.cdrrpt.startDate;
		var maxEndDateE = new Date(vm.cdrrpt.startDate);
		vm.maxStartDateLimt = new Date(maxEndDateE.getFullYear(), maxEndDateE.getMonth(), maxEndDateE.getDate()).toDateString();
		vm.maxEndDateLimt = new Date(maxEndDateE.getFullYear(), maxEndDateE.getMonth(), maxEndDateE.getDate()).toDateString();
		vm.disableEndDate=false;
		
	}
	
	
	vm.loadSubAccounts = function(){
		 $http.get('/api/accoutdetails/loadSubAccounts', {params: {"selAccType": vm.cdrrpt.selAccType}}).then(function (response){
	            vm.subAccounts = response.data.data;
	            
	        });
	}
	
	
     vm.total_count = 0;
     vm.itemsPerPage = 5;
     vm.dataLoaded=false;
     vm.showLoaded=false;
	vm.searchCDRReport = function(pageno){
		  vm.dataLoaded=false;
		vm.cdrReport = [];
		 vm.totalCDRDetails=[];
		 if(pageno==1){
			 vm.total_count = 0;
		 }
        if ((vm.cdrrpt.selAccType == undefined || vm.cdrrpt.selAccType == "") &&
            (vm.cdrrpt.startDate == undefined || vm.cdrrpt.startDate == "")/* && (vm.cdrrpt.endDate == undefined || vm.cdrrpt.endDate == "")*/) {
            vm.displayUserSrchValMsg = false;
            vm.displayMsg="Please enter at least one of the search fields";
        }else if(vm.cdrrpt.startDate== undefined || vm.cdrrpt.startDate== "" /*&& (vm.cdrrpt.endDate!="" || vm.cdrrpt.endDate !=undefined)*/){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Enter Start date";
        }else if(vm.cdrrpt.selAccType == undefined || vm.cdrrpt.selAccType == ""){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Please Select at least one Account Type";
        }else if(vm.cdrrpt.endDate== undefined && (vm.cdrrpt.startDate!="" || vm.cdrrpt.startDate !=undefined)){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Enter End date";
        }
        else if(new Date(vm.cdrrpt.endDate) < new Date(vm.cdrrpt.startDate)){
        	 vm.displayUserSrchValMsg = false;
        	 vm.displayMsg="End date should not be less than Start date";
        } else {
            vm.displayUserSrchValMsg = true;
            var parameter = JSON.stringify({
          	 startDate: vm.cdrrpt.startDate,
          	 endDate: vm.cdrrpt.startDate,
          	 accountType:vm.cdrrpt.selAccType,
          	 itemsPerPage :  vm.itemsPerPage ,
          	 selSubAccount : vm.cdrrpt.selSubAccType,
          	 pageNo :  pageno
         });
            vm.showLoaded=true;
            vm.noData=false;
            $http.post('/api/cdrrpt/searchTotalCount',parameter).then(function (response){
                    var data = response.data.data;
                        vm.totalCDRDetails = data;
                        vm.showLoaded=false;
                        if(vm.totalCDRDetails.length==0){
                        	vm.noData=true;
                        }else{
                        	vm.dataLoaded=true;
                        	
                        }
                        //vm.total_count = vm.totalCDRDetails.length;
            });
            
            
          
         /*   if(pageno==1){
            	 $http.post('/api/cdrrpt/searchTotalCount',parameter).then(function (response){
                     console.log(response);
                         var data = response.data.data;
                             console.log(data);
                             vm.totalCDRDetails = data;
                             vm.total_count = vm.totalCDRDetails.length;
                        	 $http.post('/api/cdrrpt/search',parameter).then(function (response){
                                 console.log(response);
                                     var data = response.data.data;
                                         console.log(data);
                                         vm.cdrReport = data;
                                         console.log(vm.total_count);
                                        // vm.total_count = 100;
                                         
                             });
                 });
            }else{
           	 $http.post('/api/cdrrpt/search',parameter).then(function (response){
                 console.log(response);
                     var data = response.data.data;
                         console.log(data);
                         vm.cdrReport = data;
                         console.log(vm.total_count);
                        // vm.total_count = 100;
                         
             });
            }*/
            
     
           
        }
           
	}
	vm.exportCDRAction= function(){
		 vm.fileAccName = 'CDRReport';
		 alasql("SELECT msgId as MessageID,systemId as AccountType,mobile as MobileNo,message as Message,sender as Sender,submitTime as SubmitTime,deliverTime as DeliverTime,deliverStatus as DeliveryStatus,errorCode as ErrorCode INTO XLSX(" + "'" + vm.fileAccName + ".xlsx" + "'" + ", {headers:true}) FROM ?", [vm.totalCDRDetails]);
		 
		 
	}
	
	
	
	
}]);