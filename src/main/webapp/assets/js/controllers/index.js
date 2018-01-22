/** attach controllers to this module 
 * if you get 'unknown {x}Provider' errors from angular, be sure they are
 * properly referenced in one of the module dependencies in the array.
 * below, you can see we bring in our services and constants modules 
 * which avails each controller of, for example, the `config` constants object.
 **/
define([
    './index-controller',
    './dashboard-controller',
    './user-list-controller',
    './user-form-controller',
    './role-list-controller',
    './role-form-controller',
    './menu-list-controller',
    './menu-form-controller',
    './post-list-controller',
    './post-form-controller',
    './comment-list-controller',
    './group-config-controller',
    './system-config-controller',
    './column-config-controller',
    './category-config-controller',
    './login-access-controller',
    './column-access-controller',
    './attachment-upload-controller',
    './attachment-list-controller'
], function () {});
