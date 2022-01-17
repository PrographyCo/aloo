<?php
namespace App\DataTables\Vendor;

use App\Models\Order;
use Carbon\Carbon;
use Illuminate\Database\Eloquent\Builder;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class OrderDataTable extends DataTable
{

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
                $actions = '<a href="'. route('vendor.orders.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>';
                $actions .= (in_array($query->status,['unconfirmed','confirmed by vendor']))?
                    '<a href="'. route('vendor.orders.'.(($query->status==="unconfirmed")?'confirm':'ready') ,$query) .'" class="rounded mx-1"><i class="ri-'.(($query->status==="unconfirmed")?'check':'check-double').'-fill" title="'.(($query->status==="unconfirmed")?'confirm':'ready').'"></i></a>
                        <div style="cursor: pointer" onclick="confirmDelete('.$query->id.')" class="rounded mx-1 btn-danger btn"><i class="ri-delete-bin-fill"></i></div>
                        ':'';

                return $actions;
            })
            ->rawColumns([
                'action',
                'status'
            ]);
    }

    public function query(Order $model)
    {
        return $model->newQuery()->with([
            'customer',
            'branch',
        ])->select([
            'orders.id',
            'orders.total_price',
            'orders.customer_id',
            'orders.branch_id',
            'orders.status',
            'orders.created_at as create_date',
            'orders.updated_at as update_date',
        ])->withSum('items', 'amount')->withCount('items')
            ->whereHas('branch',function (Builder $q){
                $q->where('vendor_id','=', request()->user('web-vendors')->id);
            });
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('order-table')
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
            Column::make('customer.name')->title('customer name'),
            Column::make('customer.phone')->title('customer phone'),
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

    protected function filename()
    {
        return 'all_order_' . date('YmdHis');
    }
}
