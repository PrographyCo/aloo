<?php
namespace App\DataTables\Admin\Vendor;

use App\Http\Controllers\Controller;
use App\Models\Item;
use Carbon\Carbon;
use Yajra\DataTables\Html\Button;
use Yajra\DataTables\Html\Column;
use Yajra\DataTables\Services\DataTable;

class ItemDataTable extends DataTable
{
    protected $vendor_id;
    public function __construct($vendor_id)
    {
        $this->vendor_id = $vendor_id;
        Item::$show = true;
    }

    /**
     * Build DataTable class.
     *
     * @param mixed $query Results from query() method.
     * @return \Yajra\DataTables\DataTableAbstract
     */
    public function dataTable($query)
    {
        return datatables()
            ->eloquent($query)
            ->editColumn('created_at', static function ($query){
                return Carbon::parse($query->created_at)->format('Y-m-d');
            })
            ->editColumn('brief_desc_'.Controller::$lang, static function ($query){
                return \WEBHelper::truncate($query->{'brief_desc_'.Controller::$lang}, 50);
            })
            ->addColumn('action', static function ($query){
//                todo make plus to show all data about order
                return 'action';
            })->rawColumns([
                'action',
                'brief_desc_'.Controller::$lang,
            ]);
    }


    public function query(Item $model)
    {
        return $model
            ->newQuery()
            ->where('vendor_id', $this->vendor_id)
            ->with('category:id,name_'.Controller::$lang);
    }


    public function html()
    {
        return $this->builder()
            ->setTableId('item-table')
            ->columns($this->getColumns())
            ->ajax(route('admin.vendor.datatable', [$this->vendor_id, 'item']))
//            ->dom('Bfrtip')
            ->orderBy(0)
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
            Column::make('name_'.Controller::$lang)->title(__('web/vendor/datatable.item.name')),
            Column::make('category.name_'.Controller::$lang)->title(__('web/vendor/datatable.item.desecration')),
            Column::make('amount')->title(__('web/vendor/datatable.item.amount')),
            Column::make('amount_type')->title(__('web/vendor/datatable.item.amount_type')),
            Column::make('brief_desc_'.Controller::$lang)->title(__('web/vendor/datatable.item.brief_desc')),
            Column::make('created_at')->title(__('web/vendor/datatable.item.created_at')),
//            Column::computed('action')
//                ->exportable(false)
//                ->printable(false)
//                ->width(60)
//                ->addClass('text-center'),
        ];
    }

    /**
     * Get filename for export.
     *
     * @return string
     */
    protected function filename()
    {
        return 'items_' . date('YmdHis');
    }
}
