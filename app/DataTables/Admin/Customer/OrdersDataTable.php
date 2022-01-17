<?php
namespace App\DataTables\Admin\Customer;

use App\Http\Controllers\Controller;
use App\Models\Car;
use App\Models\Customer;
use App\Models\Order;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class OrdersDataTable extends DataTable
{
    protected Customer $customer;
    public function __construct($customer)
    {
        $this->customer = $customer;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('create_date', function ($query){
                return Carbon::parse($query->update_date)->format('Y-m-d');
            })
            ->editColumn('total_price', function ($query){
                return \WEBHelper::priceToText($query->total_price);
            })
            ->editColumn('status', function ($query){
                return \WEBHelper::textToSpan($query->status);
            })
            ->addColumn('create_time', function ($query){
                return Carbon::parse($query->update_date)->format('H:i:s');
            })
            ->addColumn('action', function ($query) {
                return '<a href="'. route('admin.order.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<div style="cursor: pointer; display: contents" class="text-blue" onclick="changeOrder(`' . route('admin.order.changeDeliver', $query) . '`, ' . $query->id . ', ' . $query->car_id . ', this)" class="rounded mx-1"><i class="bi bi-bicycle"></i></div>';
            })
            ->rawColumns([
                'action',
                'status'
            ]);
    }


    public function query(Order $model)
    {
        return $model
            ->newQuery()->where('customer_id', $this->customer->id)->with([
                'branch',
                'car',
            ])->withSum('items', 'amount')->withCount('items');
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('car-table')
            ->columns($this->getColumns())
            ->minifiedAjax()
//            ->dom('Bfrtip')
            ->orderBy(1)
            ->buttons(
                Button::make('create'),
                Button::make('export'),
                Button::make('print'),
                Button::make('reset'),
                Button::make('reload')
            );
    }


    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('car.name')->title('driver name'),
            Column::make('car.phone')->title('driver phone'),
            Column::make('branch.name')->title('branch name'),
            Column::make('total_price')->title('order price'),
            Column::make('items_sum_amount')->title('total items amount'),
            Column::make('items_count')->title('items types N.'),
            Column::make('status')->title('order status'),
            Column::make('create_date')-> title('order date'),
            Column::make('create_time', 'create_date')-> title('order time'),
            Column::computed('action')
                ->exportable(false)
                ->printable(false),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'order_' . date('YmdHis');
    }
}
