<?php
namespace App\DataTables\Driver;

use App\Http\Controllers\Controller;
use App\Models\Car;
use App\Models\Order;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class CarOrderDataTable extends DataTable
{
    protected Car $car;
    public function __construct(Car $car)
    {
        $this->car = $car;
    }


    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('create_date', function ($query){
                return Carbon::parse($query->update_date)->format('Y-m-d');
            })
            ->editColumn('status', function ($query){
                return \WEBHelper::textToSpan($query->status);
            })
            ->addColumn('create_time', function ($query){
                return Carbon::parse($query->update_date)->format('H:i:s');
            })
            ->rawColumns([
                'status'
            ]);
    }


    public function query(Order $model)
    {
        return $model->newQuery()
            ->where('car_id', $this->car->id)->with([
            'customer',
            'branch',
        ])->select([
            'orders.id',
            'orders.customer_id',
            'orders.branch_id',
            'orders.status',
            'orders.created_at as create_date',
            'orders.updated_at as update_date',
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

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('id'),
            Column::make('customer.name')->title('customer name'),
            Column::make('customer.phone')->title('customer phone'),
            Column::make('branch.name')->title('branch name'),
            Column::make('items_sum_amount')->title('total items amount'),
            Column::make('items_count')->title('items types N.'),
            Column::make('status')->title('order status'),
            Column::make('create_date')-> title('order date'),
            Column::make('create_time', 'create_date')-> title('order time'),
//            Column::computed('action')
//                ->exportable(false)
//                ->printable(false),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'order_car_' . date('YmdHis');
    }
}
