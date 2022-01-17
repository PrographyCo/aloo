<?php

namespace App\Providers;

use App\Helpers\APIHelper;
use Illuminate\Cache\RateLimiting\Limit;
use Illuminate\Foundation\Support\Providers\RouteServiceProvider as ServiceProvider;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\RateLimiter;
use Illuminate\Support\Facades\Route;

class RouteServiceProvider extends ServiceProvider
{
    /**
     * The path to the "home" route for your application.
     *
     * This is used by Laravel authentication to redirect users after login.
     *
     * @var string
     */
    public const HOME = '/home';

    /**
     * The controller namespace for the application.
     *
     * When present, controller route declarations will automatically be prefixed with this namespace.
     *
     * @var string|null
     */
//     protected $namespace = 'App\\Http\\Controllers';

    /**
     * Define your route model bindings, pattern filters, etc.
     *
     * @return void
     */
    public function boot()
    {
        $this->configureRateLimiting();

        $this->routes(function () {

            $this->getApiRoutes();
            $this->getWebRoutes();

        });
    }

    /**
     * Create API Routing Settings
     *
     * @return void
     */
    protected function getApiRoutes()
    {
        foreach (config('app.api_versions') as $version)
        {
            $version = APIHelper::getVersion($version,$prefix, $namespace);
            Route::prefix($prefix)
                ->middleware(['api','api_version:'.$version,'checkLanguage'])
                ->namespace($namespace)
                ->group(base_path("routes/api/api_{$version}.php"));
        }
    }

    /**
     * Create Web Routing Settings
     *
     * @return void
     */
    protected function getWebRoutes()
    {
        Route::middleware('web')
            ->namespace($this->namespace)
            ->group(base_path('routes/web.php'));
    }

    /**
     * Configure the rate limiters for the application.
     *
     * @return void
     */
    protected function configureRateLimiting()
    {
        RateLimiter::for('api', function (Request $request) {
            return Limit::perMinute(60)->by(optional($request->user())->id ?: $request->ip());
        });
    }
}
