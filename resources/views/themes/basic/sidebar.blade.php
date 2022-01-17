
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item">
            <a class="nav-link collapsed" href="{{ route('admin.dashboard') }}">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#orders-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bi bi-list-check"></i><span>{{ __('web/admin/menu.order.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="orders-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.order.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.all') }}</span>
                    </a>
                </li>
            </ul>
        </li>


        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#vendor-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-store-2-fill"></i><span>{{ __('web/admin/menu.vendors.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="vendor-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.vendor.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.vendors.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.vendor.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.vendors.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#driver-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-car-fill"></i><span>{{ __('web/admin/menu.drivers.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="driver-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.driver.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.drivers.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.driver.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.drivers.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>
        <!-- End Forms Nav -->
        <li class="nav-heading">Pages</li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#">
                <i class="bi bi-person"></i>
                <span>Profile</span>
            </a>
        </li><!-- End Profile Page Nav -->
    </ul>

</aside><!-- End Sidebar-->
