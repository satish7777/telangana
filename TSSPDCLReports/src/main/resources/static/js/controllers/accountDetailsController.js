'use strict';

app.controller('accountDetailsController', ['$scope','$http',  function($scope, $http) {
	
	var vm = $scope;
	vm.account={};
     vm.displayUserSrchValMsg = true;
     vm.noData=false;
     var maxDate = new Date();
     vm.minDate = new Date(maxDate.getFullYear(), maxDate.getMonth()-3, maxDate.getDate()).toDateString();
     vm.maxLimit=new Date().toDateString();
	
     vm.sort = function(keyname){
         $scope.sortKey = keyname;   //set the sortKey to the param passed
         $scope.reverse = !$scope.reverse; //if true make it false and vice versa
     }
 	
	
	vm.loadSubAccounts = function(){
		 $http.get('/api/accoutdetails/loadSubAccounts', {params: {"selAccType": vm.account.selAccType}}).then(function (response){
	            vm.subAccounts = response.data.data;
	            
	        });
	}
	
	
	vm.searchAccountWiseReport = function(){
        if ((vm.account.selAccType == undefined || vm.account.selAccType == "") && (vm.account.selSubAccType == undefined || vm.account.selSubAccType == "") &&
            (vm.account.startDate == undefined || vm.account.startDate == "") && (vm.account.endDate == undefined || vm.account.endDate == "")) {
            vm.displayUserSrchValMsg = false;
            vm.displayMsg="Please enter at least one of the search fields";
        }else if(vm.account.startDate== undefined && (vm.account.endDate!="" || vm.account.endDate !=undefined)){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Enter Start date";
        }else if(vm.account.selAccType == undefined || vm.account.selAccType == ""){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Please Select at least one Account Type";
        }else if(vm.account.endDate== undefined && (vm.account.startDate!="" || vm.account.startDate !=undefined)){
        	vm.displayUserSrchValMsg = false;
       	 	vm.displayMsg="Enter End date";
        }
        else if(new Date(vm.account.endDate) < new Date(vm.account.startDate)){
        	 vm.displayUserSrchValMsg = false;
        	 vm.displayMsg="End date should not be less than Start date";
        } else {
            vm.displayUserSrchValMsg = true;
            var parameter = JSON.stringify({
          	 startDate: vm.account.startDate,
          	 endDate: vm.account.endDate,
          	 accountType:vm.account.selAccType,
          	 subAccountType:vm.account.selSubAccType
         });
            
            vm.noData=false;  
     	 $http.post('/api/accoutdetails/search',parameter).then(function (response){
                   var data = response.data.data;
                       vm.accountWiseReport = data;
                       if(vm.accountWiseReport.length==0){
                    	   vm.noData=true;
                       }

           });
           
        }
           
	}
	
	vm.exportAccountAction= function(){
		if(vm.accountWiseReport==undefined || vm.accountWiseReport.length==0){
			
		}else{
			 vm.fileAccName = 'AccountWiseReport';
			 alasql("SELECT accountName as AccountName,inputDay as Date,submitted as Submitted,delivered as Delivered,submitFailed as Failed INTO XLSX(" + "'" + vm.fileAccName + ".xlsx" + "'" + ", {headers:true}) FROM ?", [vm.accountWiseReport]);
			 
		}
		
		 
	}
	
	
	
}]);