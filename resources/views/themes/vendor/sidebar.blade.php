
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item">
            <a class="nav-link collapsed" href="{{ route('vendor.dashboard') }}">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#orders-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bi bi-list-check"></i><span>{{ __('web/vendor/menu.order.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="orders-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.orders.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.order.all') }}</span>
                    </a>
                </li>
            </ul>
        </li>


        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#branch-sidebar" data-bs-toggle="collapse" href="#">
                <i class="ri-store-2-fill"></i><span>{{ __('web/vendor/menu.branch.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="branch-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.branch.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.branch.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('vendor.branch.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.branch.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#item-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bx bxs-grid"></i><span>{{ __('web/vendor/menu.item.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="item-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.item.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.item.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('vendor.item.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.item.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#category-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bx bxs-bookmarks"></i><span>{{ __('web/vendor/menu.category.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="category-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.category.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.category.all') }}</span>
                    </a>
                </li>

                <li><a href="{{ route('vendor.category.create') }}">
                    <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.category.create') }}</span>
                </a></li>
            </ul>
        </li>

        @if(Auth::user()->isRestaurant())
            <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#extra-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bx bxs-extension"></i><span>{{ __('web/vendor/menu.extra.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="extra-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.extra.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.extra.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('vendor.extra.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.extra.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" data-bs-target="#drink-sidebar" data-bs-toggle="collapse" href="#">
                <i class="bx bxs-drink"></i><span>{{ __('web/vendor/menu.drink.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
            </a>
            <ul id="drink-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                <li>
                    <a href="{{ route('vendor.drink.index') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.drink.all') }}</span>
                    </a>
                </li>

                <li>
                    <a href="{{ route('vendor.drink.create') }}">
                        <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.drink.create') }}</span>
                    </a>
                </li>
            </ul>
        </li>

            <li class="nav-item">
                <a class="nav-link collapsed" data-bs-target="#offer-sidebar" data-bs-toggle="collapse" href="#">
                    <i class="bx bxs-drink"></i><span>{{ __('web/vendor/menu.offers.main') }}</span><i class="bi bi-chevron-down ms-auto"></i>
                </a>
                <ul id="offer-sidebar" class="nav-content collapse" data-bs-parent="#sidebar-nav">
                    <li>
                        <a href="{{ route('vendor.offer.index') }}">
                            <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.offers.all') }}</span>
                        </a>
                    </li>

                    <li>
                        <a href="{{ route('vendor.offer.create') }}">
                            <i class="bi bi-circle"></i><span>{{ __('web/vendor/menu.offers.create') }}</span>
                        </a>
                    </li>
                </ul>
            </li>
        @endif
    </ul>

</aside>
