<?php
namespace App\DataTables\Admin;

use App\Http\Controllers\Controller;
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

class CityDataTable extends DataTable
{

    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })->addColumn('action', static function ($query){
                return '<a href="#" onclick=\'editCity('.json_encode($query->load('price')).')\' class="rounded mx-1"><i class="ri-pencil-fill"></i></a>';
            })->rawColumns([
                'action',
                'img',
            ]);
    }

    public function query(City $model)
    {
        return $model
            ->newQuery()
            ->with('price');
    }

    public function html()
    {
        return $this->builder()
            ->setTableId('city-table')
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
            Column::make('name_en')->title(__('web/admin/datatable.city.name_en')),
            Column::make('name_ar')->title(__('web/admin/datatable.city.name_ar')),
            Column::make('price.price')->title(__('web/admin/datatable.city.price')),
            Column::make('created_at')->title(__('web/admin/datatable.city.created_at')),
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
        return 'city_' . date('YmdHis');
    }
}
