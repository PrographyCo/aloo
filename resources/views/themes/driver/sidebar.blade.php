
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item">
            <a class="nav-link collapsed" href="{{ route('driver.dashboard') }}">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li>

{{--        <li class="nav-item">--}}
{{--            <a class="nav-link collapsed" data-bs-target="#orders-sidebar" data-bs-toggle="collapse" href="#">--}}
{{--                <i class="bi bi-list-check"></i><span>{{ __('web/vendor/menu.order.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>--}}
{{--            </a>--}}
{{--            <ul id="orders-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">--}}
{{--                <li>--}}
{{--                    <a href="{{ route('admin.order.index') }}">--}}
{{--                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.all') }}</span>--}}
{{--                    </a>--}}
{{--                </li>--}}
{{--            </ul>--}}
{{--        </li>--}}


        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#branch-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-store-2-fill"></i><span>{{ __('web/driver/menu.car.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="branch-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('driver.car.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/driver/menu.car.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('driver.car.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/driver/menu.car.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>
        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#branch-orders" data-bs-toggle="collapse" href="#">
                <i class="bi bi-list-check"></i><span>{{ __('web/vendor/menu.order.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="branch-orders" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('driver.orders.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.order.all') }}</span>
                    </a>
                </li>
            </ul>
        </li>

{{--        <li class="nav-item">--}}
{{--            <a class="nav-link collapsed" data-bs-target="#item-sidebar" data-bs-toggle="collapse" href="#">--}}
{{--                <i class="ri-store-2-fill"></i><span>{{ __('web/driver/menu.item.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>--}}
{{--            </a>--}}
{{--            <ul id="item-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">--}}
{{--                <li>--}}
{{--                    <a href="{{ route('vendor.item.index') }}">--}}
{{--                        <i class="bi bi-circle"></i><span>{{ __('web/driver/menu.item.all') }}</span>--}}
{{--                    </a>--}}
{{--                </li>--}}

{{--                <li>--}}
{{--                    <a href="{{ route('vendor.item.create') }}">--}}
{{--                        <i class="bi bi-circle"></i><span>{{ __('web/driver/menu.item.create') }}</span>--}}
{{--                    </a>--}}
{{--                </li>--}}
{{--            </ul>--}}
{{--        </li>--}}

{{--        <li class="nav-item">--}}
{{--            <a class="nav-link collapsed" data-bs-target="#category-sidebar" data-bs-toggle="collapse" href="#">--}}
{{--                <i class="ri-store-2-fill"></i><span>{{ __('web/driver/menu.category.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>--}}
{{--            </a>--}}
{{--            <ul id="category-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">--}}
{{--                <li>--}}
{{--                    <a href="{{ route('vendor.category.index') }}">--}}
{{--                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.category.all') }}</span>--}}
{{--                    </a>--}}
{{--                </li>--}}

{{--                <li>--}}
{{--                    <a href="{{ route('vendor.category.create') }}">--}}
{{--                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.category.create') }}</span>--}}
{{--                    </a>--}}
{{--                </li>--}}
{{--            </ul>--}}
{{--        </li>--}}


{{--        <li class="nav-heading">Pages</li>--}}

{{--        <li class="nav-item">--}}
{{--            <a class="nav-link collapsed" href="#">--}}
{{--                <i class="bi bi-person"></i>--}}
{{--                <span>Profile</span>--}}
{{--            </a>--}}
{{--        </li>--}}
    </ul>

</aside>
