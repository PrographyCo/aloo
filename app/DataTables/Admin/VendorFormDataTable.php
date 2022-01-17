<?php
namespace App\DataTables\Admin;

use App\Http\Controllers\Controller;
use App\Models\Bank;
use App\Models\City;
use App\Models\Driver;
use App\Models\Order;
use App\Models\Vendor;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Html\Editor\Editor;
use Yajra\DataTables\Html\Editor\Fields;
use Yajra\DataTables\Services\DataTable;

class VendorFormDataTable extends DataTable
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
                    '<a href="'. route('admin.vendor.edit' ,$query) .'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'logo',
                'action',
            ]);
    }

    public function query(Vendor $model)
    {
        return $model
            ->newQuery()
            ->where('confirm', 0)
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

    /**
     * Get columns.
     *
     * @return array
     */
    protected function getColumns()
    {
        return [
            Column::make('logo'),
            Column::make('legalName')->title(__('web/admin/datatable.vendor.legalName')),
            Column::make('brandName')->title(__('web/admin/datatable.vendor.brandName')),
            Column::make('phone')->title(__('web/admin/datatable.vendor.phone')),
            Column::make('supported_vendor.name_'.Controller::$lang)->title(__('web/admin/datatable.vendor.type')),
            Column::make('commercialNo')->title(__('web/admin/datatable.vendor.commercialNo')),
            Column::make('created_at')->title(__('web/admin/datatable.vendor.create_at')),
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
        return 'driver_' . date('YmdHis');
    }
}
