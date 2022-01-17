<?php
namespace App\DataTables\Admin;

use App\Models\Bank;
use App\Models\KitchenType;
use App\Models\Package;
use App\Models\RestaurantTypes;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class PackagesDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->addColumn('action', static function ($query){
                return '<a href="'.route('admin.settings.packages.edit', $query).'" class="rounded mx-1"><i class="ri-pencil-fill"></i></a>' .
                    '<div style="cursor: pointer" onclick="confirmDelete('.$query->id.')" class="rounded mx-1 btn-danger btn"><i class="ri-delete-bin-fill"></i></div>';
            })
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            });
    }

    public function query(Package $model)
    {
        return $model
            ->newQuery();
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('kitchen-table')
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
            Column::make('name_en')->title(__('web/admin/datatable.bank.name_en')),
            Column::make('name_ar')->title(__('web/admin/datatable.bank.name_ar')),
            Column::make('price')->title(__('web/admin/datatable.package.price')),
            Column::make('orders')->title(__('web/admin/datatable.package.orders')),
            Column::make('days')->title(__('web/admin/datatable.package.days')),
            Column::make('type')->title(__('web/admin/datatable.package.type')),
            Column::make('discount')->title(__('web/admin/datatable.package.discount')),
            Column::make('discount_type')->title(__('web/admin/datatable.package.dType')),
            Column::make('created_at')->title(__('web/admin/datatable.bank.created_at')),
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
        return 'restaurant_types_' . date('YmdHis');
    }
}
