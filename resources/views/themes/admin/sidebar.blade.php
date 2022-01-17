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

                <li>
                    <a href="{{ route('admin.order.datatable.new') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.new') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.order.datatable.cancel') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.cancel') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.order.datatable.completed') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.completed') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.order.datatable.deliver') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.order.deliver') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#form-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-chat-new-fill"></i><span>{{ __('web/admin/menu.form.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="form-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.register.vendor') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.form.vendor') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.register.driver') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.form.driver') }}</span>
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

                <li>
                    <a href="{{ route('admin.vendor.datatable.restaurant') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.vendors.restaurant') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.vendor.datatable.pharmacy') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.vendors.pharmacy') }}</span>
                    </a>
                </li>


                <li>
                    <a href="{{ route('admin.vendor.datatable.supermarket') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.vendors.supermarket') }}</span>
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

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#customer-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bi bi-people"></i><span>{{ __('web/admin/menu.customer.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="customer-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.customer.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.customer.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('admin.customer.datatable.todayNew') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.customer.new') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#settings-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-settings-3-fill"></i><span>{{ __('web/admin/menu.settings.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="settings-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('admin.settings.general') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.general') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.city.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.city') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.privacy.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.privacy') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.bank.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.bank') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.kitchen.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.kitchen') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.restaurant.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.restaurant') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.packages.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.packages') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.faq.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.faq') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.clientService') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.clientService') }}</span>
                    </a>
                </li>
                <li>
                    <a href="{{ route('admin.settings.about') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/admin/menu.settings.about') }}</span>
                    </a>
                </li>
            </ul>
        </li>
    </ul>

</aside>
