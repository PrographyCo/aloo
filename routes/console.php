<?php

use Illuminate\Foundation\Inspiring;
use Illuminate\Support\Facades\Artisan;

/*
|--------------------------------------------------------------------------
| Console Routes
|--------------------------------------------------------------------------
|
| This file is where you may define all of your Closure based console
| commands. Each Closure is bound to a command instance allowing a
| simple approach to interacting with each command's IO methods.
|
*/

Artisan::command('inspire', function () {
    $this->comment(Inspiring::quote());
})->purpose('Display an inspiring quote');

Artisan::command('make:apiController {name} {version?}', function () {
    $version = $this->arguments()['version'];
    $name = $this->arguments()['name'];

    if ($version===null)
    {
        $version = config('app.api_latest');
    }

    $version = APIHelper::getVersion($version, $p, $n);
    if (in_array($version, APIHelper::getAllowedVersions(), true) === false)
    {
        $this->error('Selected Api version Is Not Allowed');
        exit();
    }

    $name = explode('/', $name);
    foreach ($name as $key => $item)
    {
        $name[$key] = ucwords($item);
    }

    $name = ucwords(implode('\\',$name));
    $controllerName = $n.'\\'.$name.(str_ends_with(strtolower($name),'controller')?'':'Controller');

    Artisan::call('make:controller',['name' => $controllerName]);

})->purpose('Make New Controller For API Based on a version');
