<?php
namespace App\DataTables\Admin;

use App\Http\Controllers\Controller;
use App\Models\Vendor;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class SupermarketVendorDataTable extends DataTable
{
    public function __construct()
    {
        Vendor::$show = true;
    }

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('logo', static function ($query){
                return '<a href="'.  route('admin.vendor.show', $query).'"><img src="'. $query->logo .'" class="rounded-circle" style="max-height: 40px" alt=""></a>';
            })
            ->addColumn('action', static function ($query){
                return '<a href="'. route('admin.vendor.show' ,$query) .'" class="rounded mx-1"><i class="ri-eye-fill"></i></a>'.
                    '<a href="'. route('admin.vendor.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>'.
                    '<a href="#" onclick=\'sendMoney('.$query->id.','.($query->wallet() - $query->transactions->sum('amount')).')\' class="rounded mx-1" style="color: green"><i class="ri-money-dollar-circle-fill ri-xl"></i></a>';
            })
            ->addColumn('wallet', static function ($query) {
                return number_format($query->wallet() - $query->transactions->sum('amount'),2,'.','');
            })
            ->rawColumns([
                'logo',
                'action',
            ]);
    }

    public function query(Vendor $model)
    {
        return $model
            ->newQuery()
            ->where('supported_vendor_id', 1)
            ->with([
            'supported_vendor:id,name_'.Controller::$lang
        ]);
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('vendor-table')
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
            Column::make('logo'),
            Column::make('legalName')->title(__('web/admin/datatable.vendor.legalName')),
            Column::make('brandName')->title(__('web/admin/datatable.vendor.brandName')),
            Column::make('phone')->title(__('web/admin/datatable.vendor.phone')),
            Column::make('commercialNo')->title(__('web/admin/datatable.vendor.commercialNo')),
            Column::make('wallet')->title('Money'),
            Column::make('created_at')->title(__('web/admin/datatable.vendor.create_at')),
            Column::computed('action')
                ->exportable(false)
                ->printable(false)
                ->width(60)
                ->addClass('text-center'),
        ];
    }

    protected function filename()
    {
        return 'vendor_supermarket_' . date('YmdHis');
    }
}
