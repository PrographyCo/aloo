<?php
namespace App\DataTables\Admin\Customer;

use App\Http\Controllers\Controller;
use App\Models\Car;
use App\Models\Customer;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class NewMothCustomerDataTable extends DataTable
{
    public function __construct()
    {
        Customer::$show = true;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('ban', static function ($query){
                return \WEBHelper::textToSpan($query->ban? 'ban' : 'not ban');
            })
            ->editColumn('img', static function ($query){
                return '<a href="'.route('admin.customer.show', $query).'"><img src="'.$query->img.'" class="rounded-circle" style="max-height: 64px" alt=""></a>';
            })
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('wallet.amount', static function ($query){
                return \WEBHelper::priceToText($query->wallet->amount ?? '0');
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('admin.customer.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<form action='. route('admin.customer.update', $query) .' method="post">'.method_field('put').'
                        <input name="_token" value="'.csrf_token().'" hidden>
                        <button type="submit" class="btn-sm btn-primary">'.($query->ban ? '<i class="bi bi-unlock-fill"></i>' : '<i class="bi bi-lock-fill"></i>') .'</button>
                    </form>';
            })
            ->filterColumn('rates_count', function($query, $keyword) {
                return $query->having('rates_count', $keyword);
            })
            ->rawColumns([
                'action',
                'ban',
                'img',
            ]);
    }


    public function query(Customer $model)
    {
        return $model
            ->newQuery()
            ->whereMonth('created_at', now()->month)->with([
                'city:id,name_' . Controller::$lang,
                'wallet:customer_id,amount',
            ])->withCount('rates');
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
            Column::make('img')->title(__('web/admin/datatable.customer-main.img')),
            Column::make('name')->title(__('web/admin/datatable.customer-main.name')),
            Column::make('phone')->title(__('web/admin/datatable.customer-main.phone')),
            Column::make('email')->title(__('web/admin/datatable.customer-main.email')),
            Column::make('gender')->title(__('web/admin/datatable.customer-main.gender')),
            Column::make('city.name_' . Controller::$lang)->title(__('web/admin/datatable.customer-main.city')),
            Column::make('wallet.amount')->title(__('web/admin/datatable.customer-main.wallet_amount')),
            Column::make('ban')->title(__('web/admin/datatable.customer-main.ban')),
            Column::make('rates_count')->title(__('web/admin/datatable.customer-main.rates_count')),
            Column::make('created_at')->title(__('web/admin/datatable.customer-main.created_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'customer_' . date('YmdHis');
    }
}
