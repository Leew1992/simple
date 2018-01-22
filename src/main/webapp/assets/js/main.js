/**
 * 定义RequireJS配置
 */
require.config({

    paths: {
        'angular': '../lib/angular/angular',
        'angular-route': '../lib/angular-route/angular-route',
        'domReady': '../lib/requirejs-domready/domReady',
        'bootstrap': '../lib/bootstrap/bootstrap.min',
        'jquery': '../lib/jquery/jquery.min',
        'jqueryTmpl': '../lib/jqueryTmpl/jquery.tmpl.min',
        'jqueryTreeTable': '../lib/jqueryTreeTable/jquery.treeTable.min',
        'ztree': '../lib/ztree/js/jquery.ztree.all.min',
        'datetimepicker': '../lib/datetimepicker/bootstrap-datetimepicker.min',
        'bootstrapTable': '../lib/bootstrap-table/bootstrap-table.min',
        'bootbox': '../lib/bootbox/bootbox.min',
        'Chart': '../lib/chart/Chart.min',
        'summernote': '../lib/summernote/summernote'
    },
    shim: {
        'angular': {
            exports: 'angular'
        },
        'angular-route': {
            deps: ['angular'],
            exports: 'angular-route'
        },
        'bootstrap': {
        	deps: ['jquery'],
        	exports: 'bootstrap'
        },
        'ztree': {
        	deps: ['jquery'],
        	exports: 'ztree'
        },
        'datetimepicker': {
        	deps: ['bootstrap'],
        	exports: 'datetimepicker'
        },
        'bootstrapTable': {
        	deps: ['bootstrap'],
        	exports: 'bootstrapTable'
        },
        'jqueryTmpl': {
        	deps: ['jquery'],
        	exports: 'jqueryTmpl'
        },
        'jqueryTreeTable': {
        	deps: ['jquery'],
        	exports: 'jqueryTreeTable'
        },
        'bootbox': {
        	exports: 'bootbox'
        },
        'Chart': {
        	exports: 'Chart'
        },
        'summernote': {
        	deps: ['jquery', 'bootstrap'],
        	exports: 'summernote'
        }
    }
});

//手动启动ng  
define(["routes"], function () {  
    //使用bootstrap方法启动Angular应用  
    angular.bootstrap(document, ["app"]);  
});