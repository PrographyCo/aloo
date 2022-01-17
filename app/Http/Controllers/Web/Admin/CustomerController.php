<?php

namespace App\Http\Controllers\Web\Admin;

use App\DataTables\Admin\Customer\CustomerDataTable;
use App\DataTables\Admin\Customer\NewMothCustomerDataTable;
use App\DataTables\Admin\Customer\OrdersDataTable;
use App\Http\Controllers\Controller;
use App\Models\Customer;

class CustomerController extends Controller
{
    public function index(CustomerDataTable $dataTable)
    {
        return $dataTable->render('web.admin.customer.index', [
            'title' => 'all customer'
        ]);    }

    public function show(Customer $customer)
    {
        $datatable = new OrdersDataTable($customer);
        $customer->load([
            'wallet',
            'orders',
            'city',
        ])->loadCount(['orders', 'rates', 'places', 'favorites', 'cartItem'])
            ->loadSum('orders', 'total_price')
            ->loadSum('cartItem', 'total_price');

        return $datatable->render('web.admin.customer.show.show', [
            'customer' => $customer,
        ]);
    }

    public function update(Customer $customer)
    {
        $customer->ban = !$customer->ban;
        $customer->save();
        return redirect()->route('admin.customer.show', $customer)->with('success', 'change customer ban (now is '.($customer->ban?null:'not').' ban )');
    }

    public function todayNew(NewMothCustomerDataTable $dataTable)
    {
        return $dataTable->render('web.admin.customer.index', [
            'title' => 'new customer in this month'
        ]);
    }

    public function destroy(Customer $customer)
    {
        $customer->delete();
        return redirect()->route('admin.customer.index')->with('success', 'customer has been deleted');
    }
}
